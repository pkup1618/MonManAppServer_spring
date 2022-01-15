package com.example.MonManAppServer_spring.controllers;

import com.example.MonManAppServer_spring.models.EarningDB;
import org.springframework.expression.ExpressionException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.MonManAppServer_spring.repositories.EarningRepository;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EarningController {

    private final EarningRepository repository;
    private final EarningDBModelAssembler assembler;

    EarningController(EarningRepository repository, EarningDBModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }


    @GetMapping("/earnings")
    CollectionModel<EntityModel<EarningDB>> all() {

        List<EntityModel<EarningDB>> earnings = repository.findAll().stream()
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(earnings, linkTo(methodOn(EarningController.class).all()).withSelfRel());
    }


    @GetMapping("earnings/date/{date}")
    CollectionModel<EntityModel<EarningDB>> allInDate(@PathVariable Date date) {

        List<EntityModel<EarningDB>> earnings = repository.findAllByDate(date).stream()
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(earnings, linkTo(methodOn(EarningController.class).all()).withRel("earnings"));
    }


    @GetMapping("earnings/id/{id}")
    EntityModel<EarningDB> one(@PathVariable Long id) {

        EarningDB earningDB = repository.findById(id)
                .orElseThrow(() -> new ExpressionException("Такая запись отсутствует в базе данных"));

        return assembler.toModel(earningDB);
    }


    @PostMapping("/earnings")
    ResponseEntity<?> newEarningDB(@RequestBody EarningDB earning) {

        EntityModel<EarningDB> entityModel = assembler.toModel(repository.save(earning));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    @PutMapping("/earnings/{id}")
    ResponseEntity<?> replaceEarningDB(@RequestBody EarningDB newEarningDB, @PathVariable Long id) {

        EarningDB earningDB = repository.findById(id)
                .map(foundedEarning -> {
                    foundedEarning.setEarningCost(newEarningDB.getEarningCost());
                    foundedEarning.setEarningName(newEarningDB.getEarningName());
                    foundedEarning.setEarningType(newEarningDB.getEarningType());
                    foundedEarning.setCount(newEarningDB.getCount());
                    foundedEarning.setDay(newEarningDB.getDay());
                    return repository.save(foundedEarning);
                })
                .orElseGet(() -> {
                    newEarningDB.setId(id);
                    return repository.save(newEarningDB);
                });

        EntityModel<EarningDB> entityModel = assembler.toModel(earningDB);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    @DeleteMapping("/earnings/{id}")
    ResponseEntity<?> deleteEarningDB(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
