package com.project.av1.good;

import com.project.av1.InvalidException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class GoodController {
    private final GoodRepository repository;

    private final GoodModelAssembler assembler;

    private final GoodValidator validator = new GoodValidator();

    GoodController(GoodRepository repository, GoodModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/goods")
    CollectionModel<EntityModel<Good>> all() {

        List<EntityModel<Good>> goods = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(goods, linkTo(methodOn(GoodController.class).all()).withSelfRel());
    }

    @GetMapping("/goods/{code}")
    EntityModel<Good> one(@PathVariable Long code) {

        Good good = repository.findById(code) //
                .orElseThrow(() -> new GoodNotFoundException(code));

        return assembler.toModel(good);
    }

    @PostMapping("/goods")
    ResponseEntity<?> newGood(@RequestBody Good newGood) {

        try{
            validator.goodRequiredFieldsValidation(newGood);
        } catch (InvalidException error){
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed") //
                            .withDetail(error.getMessage()));
        }

        EntityModel<Good> entityModel = assembler.toModel(repository.save(newGood));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/goods/{code}")
    ResponseEntity<?> replaceGood(@RequestBody Good newGood, @PathVariable Long code) {

        try{
            validator.goodRequiredFieldsValidation(newGood);
        } catch (InvalidException error){
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed") //
                            .withDetail(error.getMessage()));
        }

        Good updatedGood = repository.findById(code)
                .map(good -> {
                    good.setName(newGood.getName());
                    good.setExpectedValue(newGood.getExpectedValue());
                    good.setAliquot(newGood.getAliquot());

                    return repository.save(good);
                }) //
                .orElseGet(() -> {
                    newGood.setCode(code);
                    return repository.save(newGood);
                });

        EntityModel<Good> entityModel = assembler.toModel(updatedGood);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/goods/{code}")
    ResponseEntity<?> deleteGood(@PathVariable Long code) {

        try {
            Good good = repository.findById(code) //
                    .orElseThrow(() -> new GoodNotFoundException(code));

            repository.deleteById(good.getCode());

            return ResponseEntity.ok().build();

        } catch(GoodNotFoundException error) {
            return ResponseEntity //
                    .status(HttpStatus.NOT_FOUND) //
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                    .body(Problem.create() //
                            .withTitle("Method not allowed") //
                            .withDetail("Good Not found"));
        }
    }
}