package com.project.av1.coveredGood;

import com.project.av1.InvalidException;
import com.project.av1.good.Good;
import com.project.av1.good.GoodRepository;
import com.project.av1.insurance.Insurance;
import com.project.av1.insurance.InsuranceRepository;
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
class CoveredGoodController {

    private final CoveredGoodRepository coveredGoodRepository;

    private final GoodRepository goodRepository;

    private final InsuranceRepository insuranceRepository;


    private final CoveredGoodModelAssembler assembler;

    private final CoveredGoodValidator validator = new CoveredGoodValidator();

    CoveredGoodController(CoveredGoodRepository coveredGoodRepository, CoveredGoodModelAssembler assembler, InsuranceRepository insuranceRepository, GoodRepository goodRepository) {
        this.coveredGoodRepository = coveredGoodRepository;
        this.goodRepository = goodRepository;
        this.insuranceRepository = insuranceRepository;
        this.assembler = assembler;
    }

    @GetMapping("/coveredGoods")
    CollectionModel<EntityModel<CoveredGood>> all() {

        List<EntityModel<CoveredGood>> coveredGoods = coveredGoodRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(coveredGoods, linkTo(methodOn(CoveredGoodController.class).all()).withSelfRel());
    }

    @GetMapping("/coveredGoods/{goodCode}")
    EntityModel<CoveredGood> one(@PathVariable Long goodCode) {

        CoveredGood good = coveredGoodRepository.findById(goodCode) //
                .orElseThrow(() -> new CoveredGoodNotFoundException(goodCode));

        return assembler.toModel(good);
    }

    @PostMapping("/insurances/{policy}/goods/{code}")
    ResponseEntity<?> newGood(@RequestBody CoveredGood newCoveredGood, @PathVariable Long policy, @PathVariable Long code) {

        Insurance insurance = insuranceRepository.findById(policy).get();
        Good good = goodRepository.findById(code).get();

        try{
            validator.coveredGoodRequiredFieldsValidation(newCoveredGood, good);
        } catch (InvalidException error){
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed") //
                            .withDetail(error.getMessage()));
        }

        newCoveredGood.setGoodCode(good.getCode());

        if(newCoveredGood.getRiskFactor() == null) {
            newCoveredGood.setTotalValue(good.getExpectedValue() * good.getAliquot()  * newCoveredGood.getDeadline());
        } else {
            newCoveredGood.setTotalValue(good.getExpectedValue() * (good.getAliquot() + newCoveredGood.getRiskFactor())  * newCoveredGood.getDeadline());
        }

        insurance.addCoveredGood(good.getCode());

        insuranceRepository.save(insurance);
        EntityModel<CoveredGood> entityModel = assembler.toModel(coveredGoodRepository.save(newCoveredGood));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/coveredGoods/{code}")
    ResponseEntity<?> replaceGood(@RequestBody CoveredGood newCoveredGood, @PathVariable Long code) {

        Good good = goodRepository.findById(code).get();

        try{
            validator.coveredGoodRequiredFieldsValidation(newCoveredGood, good);
        } catch (InvalidException error){
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed") //
                            .withDetail(error.getMessage()));
        }

        CoveredGood updatedCoveredGood = coveredGoodRepository.findById(code)
                .map(coveredGood -> {
                    coveredGood.setDeadline(newCoveredGood.getDeadline());

                    if(newCoveredGood.getRiskFactor() != null){
                        coveredGood.setRiskFactor(newCoveredGood.getRiskFactor());
                        coveredGood.setTotalValue(good.getExpectedValue() * (good.getAliquot() + newCoveredGood.getRiskFactor())  * newCoveredGood.getDeadline());
                    }

                    coveredGood.setTotalValue(good.getExpectedValue() * good.getAliquot()  * newCoveredGood.getDeadline());

                    return coveredGoodRepository.save(coveredGood);
                }) //
                .orElseGet(() -> coveredGoodRepository.save(newCoveredGood));

        EntityModel<CoveredGood> entityModel = assembler.toModel(updatedCoveredGood);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/coveredGoods/{goodCode}")
    ResponseEntity<?> deleteGood(@PathVariable Long goodCode) {

        try {
            CoveredGood good = coveredGoodRepository.findById(goodCode) //
                    .orElseThrow(() -> new CoveredGoodNotFoundException(goodCode));

            coveredGoodRepository.deleteById(good.getGoodCode());

            return ResponseEntity.ok().build();

        } catch(CoveredGoodNotFoundException error) {
            return ResponseEntity //
                    .status(HttpStatus.NOT_FOUND) //
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                    .body(Problem.create() //
                            .withTitle("Method not allowed") //
                            .withDetail("CoveredGood Not found"));
        }
    }
}