package com.example.MonManAppServer_spring.controllers;

import com.example.MonManAppServer_spring.models.DateDB;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DateDBModelAssembler implements RepresentationModelAssembler<DateDB, EntityModel<DateDB>> {

    @Override
    public EntityModel<DateDB> toModel(DateDB date) {

        return EntityModel.of(date,
                linkTo(methodOn(DateController.class).one(date.getDay())).withSelfRel(),
                linkTo(methodOn(DateController.class).all()).withRel("dates"),
                linkTo(methodOn(EarningController.class).allInDate(date.getDay())).withRel("earnings of the day"),
                linkTo(methodOn(PurchaseController.class).allInDate(date.getDay())).withRel("purchases of the day"));
    }
}
