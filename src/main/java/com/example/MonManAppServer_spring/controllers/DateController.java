package com.example.MonManAppServer_spring.controllers;

import com.example.MonManAppServer_spring.models.DateDB;
import com.example.MonManAppServer_spring.repositories.*;
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
public class DateController {

    private final DateRepository repository;
    private final DateDBModelAssembler assembler;

    DateController(DateRepository repository, DateDBModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }


    @GetMapping("/dates")
    CollectionModel<EntityModel<DateDB>> all() {

        //Вместо того, чтобы возвращать список дат, я каждую дату в отдельности преобразовываю в ресурс с доп ссылками.
        List<EntityModel<DateDB>> dates = repository.findAll().stream()
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(dates, linkTo(methodOn(DateController.class).all()).withSelfRel());
    }


    @GetMapping("/dates/{date}")
    EntityModel<DateDB> one(@PathVariable Date date) {

        DateDB dateDB = repository.findById(date)
                .orElseThrow(() -> new ExpressionException("Такая запись отсутствует в базе данных"));

        return assembler.toModel(dateDB);
    }


    @PostMapping("/dates")
    ResponseEntity<?> newDateDB(@RequestBody DateDB date) {

        EntityModel<DateDB> entityModel = assembler.toModel(repository.save(date));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    @PutMapping("/dates/{date}")
    ResponseEntity<?> replaceDateDB(@RequestBody DateDB newDateDB, @PathVariable Date date) {

        DateDB dateDB = repository.findById(date)
                .map(foundedDate -> {
                    foundedDate.setCashlessValueOnDayStart(newDateDB.getCashlessValueOnDayStart());
                    foundedDate.setCashlessValueOnDayEnd(newDateDB.getCashlessValueOnDayEnd());
                    foundedDate.setCashValueOnDayStart(newDateDB.getCashValueOnDayStart());
                    foundedDate.setCashValueOnDayEnd(newDateDB.getCashValueOnDayEnd());
                    return repository.save(foundedDate);
                })
                .orElseGet(() -> {
                    newDateDB.setDay(date);
                    return repository.save(newDateDB);
                });

        EntityModel<DateDB> entityModel = assembler.toModel(dateDB);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }


    @DeleteMapping("/dates/{date}")
    ResponseEntity<?> deleteDateDB(@PathVariable Date date) {
        repository.deleteById(date);
        return ResponseEntity.noContent().build();
    }
}
