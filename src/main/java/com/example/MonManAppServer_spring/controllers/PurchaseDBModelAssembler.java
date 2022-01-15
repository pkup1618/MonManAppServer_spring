package com.example.MonManAppServer_spring.controllers;


import com.example.MonManAppServer_spring.models.PurchaseDB;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PurchaseDBModelAssembler implements RepresentationModelAssembler<PurchaseDB, EntityModel<PurchaseDB>> {

    @Override
    public EntityModel<PurchaseDB> toModel(PurchaseDB purchase) {

        return EntityModel.of(purchase,
                linkTo(methodOn(PurchaseController.class).one(purchase.getId())).withSelfRel(),
                linkTo(methodOn(DateController.class).one(purchase.getDay())).withRel("day"));
    }
}
