package com.project.av1.coveredGood;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
class CoveredGoodModelAssembler implements RepresentationModelAssembler<CoveredGood, EntityModel<CoveredGood>> {

    @Override
    public EntityModel<CoveredGood> toModel(CoveredGood good) {

        return EntityModel.of(good,
                linkTo(methodOn(CoveredGoodController.class).one(good.getGoodCode())).withSelfRel(),
                linkTo(methodOn(CoveredGoodController.class).all()).withRel("coveredGoods"));
    }
}