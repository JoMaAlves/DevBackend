package com.project.av1.good;

import com.project.av1.good.Good;
import com.project.av1.good.GoodController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
class GoodModelAssembler implements RepresentationModelAssembler<Good, EntityModel<Good>> {

    @Override
    public EntityModel<Good> toModel(Good good) {

        return EntityModel.of(good,
                linkTo(methodOn(GoodController.class).one(good.getCode())).withSelfRel(),
                linkTo(methodOn(GoodController.class).all()).withRel("goods"));
    }
}