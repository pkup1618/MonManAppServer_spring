package com.example.MonManAppServer_spring.controllers;

import com.example.MonManAppServer_spring.models.EarningDB;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EarningDBModelAssembler implements RepresentationModelAssembler<EarningDB, EntityModel<EarningDB>> {

    @Override
    public EntityModel<EarningDB> toModel(EarningDB earning) {

        return EntityModel.of(earning,
                linkTo(methodOn(EarningController.class).one(earning.getId())).withSelfRel(),
                linkTo(methodOn(DateController.class).one(earning.getDay())).withRel("day"));
    }
}
