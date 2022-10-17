package com.project.av1.insurance;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.project.av1.coveredGood.CoveredGood;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
class InsuranceModelAssembler implements RepresentationModelAssembler<InsuranceJSONFormatter, EntityModel<InsuranceJSONFormatter>> {

    @Override
    public EntityModel<InsuranceJSONFormatter> toModel(InsuranceJSONFormatter insurance) {

        return EntityModel.of(insurance,
                linkTo(methodOn(InsuranceController.class).one(insurance.getPolicy())).withSelfRel(),
                linkTo(methodOn(InsuranceController.class).all()).withRel("insurances"));
    }
}