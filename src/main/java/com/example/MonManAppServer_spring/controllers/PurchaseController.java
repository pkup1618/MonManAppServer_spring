package com.example.MonManAppServer_spring.controllers;

import com.example.MonManAppServer_spring.models.PurchaseDB;
import com.example.MonManAppServer_spring.repositories.PurchaseRepository;
import org.springframework.expression.ExpressionException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PurchaseController {

    private final PurchaseRepository repository;
    private final PurchaseDBModelAssembler assembler;

    PurchaseController(PurchaseRepository repository, PurchaseDBModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }


    @GetMapping("/purchases")
    CollectionModel<EntityModel<PurchaseDB>> all() {

        List<EntityModel<PurchaseDB>> purchases = repository.findAll().stream()
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(purchases, linkTo(methodOn(PurchaseController.class).all()).withSelfRel());
    }


    @GetMapping("purchases/date/{date}")
    CollectionModel<EntityModel<PurchaseDB>> allInDate(@PathVariable Date date) {

        List<EntityModel<PurchaseDB>> purchases = repository.findAllByDate(date).stream()
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(purchases, linkTo(methodOn(PurchaseController.class).all()).withRel("purchases"));
    }


    @GetMapping("purchases/id/{id}")
    EntityModel<PurchaseDB> one(@PathVariable Long id) {

        PurchaseDB purchaseDB = repository.findById(id)
                .orElseThrow(() -> new ExpressionException("Такая запись отсутствует в базе данных"));

        return assembler.toModel(purchaseDB);
    }


    @PostMapping("/purchases")
    ResponseEntity<?> newPurchaseDB(@RequestBody PurchaseDB purchase) {

        EntityModel<PurchaseDB> entityModel = assembler.toModel(repository.save(purchase));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    @PutMapping("/purchases/{id}")
    ResponseEntity<?> replacePurchaseDB(@RequestBody PurchaseDB newPurchaseDB, @PathVariable Long id) {

        PurchaseDB purchaseDB = repository.findById(id)
                .map(foundedPurchase -> {
                    foundedPurchase.setPurchaseCost(newPurchaseDB.getPurchaseCost());
                    foundedPurchase.setPurchaseName(newPurchaseDB.getPurchaseName());
                    foundedPurchase.setPurchaseType(newPurchaseDB.getPurchaseType());
                    foundedPurchase.setCount(newPurchaseDB.getCount());
                    foundedPurchase.setDay(newPurchaseDB.getDay());
                    return repository.save(foundedPurchase);
                })
                .orElseGet(() -> {
                    newPurchaseDB.setId(id);
                    return repository.save(newPurchaseDB);
                });

        EntityModel<PurchaseDB> entityModel = assembler.toModel(purchaseDB);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    @DeleteMapping("/purchases/{id}")
    ResponseEntity<?> deletePurchaseDB(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}