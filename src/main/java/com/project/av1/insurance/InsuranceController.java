package com.project.av1.insurance;

import com.project.av1.InvalidException;
import com.project.av1.av2.TxtBuilder;
import com.project.av1.coveredGood.CoveredGood;
import com.project.av1.coveredGood.CoveredGoodNotFoundException;
import com.project.av1.coveredGood.CoveredGoodRepository;
import com.project.av1.good.GoodRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class InsuranceController {

    private final InsuranceRepository insuranceRepository;
    private final CoveredGoodRepository coveredGoodRepository;
    private final GoodRepository goodRepository;

    private final InsuranceValidator validator = new InsuranceValidator();
    private final InsuranceModelAssembler assembler;

    private static final TxtBuilder txtBuilder = new TxtBuilder();

    InsuranceController(InsuranceRepository insuranceRepository, InsuranceModelAssembler assembler, CoveredGoodRepository coveredGoodRepository, GoodRepository goodRepository) {

        this.insuranceRepository = insuranceRepository;
        this.assembler = assembler;
        this.coveredGoodRepository = coveredGoodRepository;
        this.goodRepository = goodRepository;
    }

    @GetMapping("/insurances")
    CollectionModel<EntityModel<InsuranceJSONFormatter>> all() {

        List<Insurance> insurances = insuranceRepository.findAll();
        List<InsuranceJSONFormatter> formattedList= new ArrayList<>();

        for(int i = 0; i < insurances.size(); i++) {
            List<CoveredGood> coveredGoodList = new ArrayList<>();

            insurances.get(i).getCoveredGoods().forEach(good -> {
                coveredGoodList.add(coveredGoodRepository.findById(good).orElseThrow(() -> new CoveredGoodNotFoundException(good)));
            });

            formattedList.add(new InsuranceJSONFormatter(insurances.get(i), coveredGoodList));
        }

        List<EntityModel<InsuranceJSONFormatter>> formattedInsurances = formattedList.stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(formattedInsurances, linkTo(methodOn(InsuranceController.class).all()).withSelfRel());
    }

    @GetMapping("/insurances/{policy}")
    EntityModel<InsuranceJSONFormatter> one(@PathVariable Long policy) {

        Insurance insurance = insuranceRepository.findById(policy) //
                .orElseThrow(() -> new InsuranceNotFoundException(policy));

        List<CoveredGood> coveredGoodList = new ArrayList<>();

        insurance.getCoveredGoods().forEach(good -> {
            coveredGoodList.add(coveredGoodRepository.findById(good).orElseThrow(() -> new CoveredGoodNotFoundException(good)));
        });

        return assembler.toModel(new InsuranceJSONFormatter(insurance, coveredGoodList));
    }

    @PostMapping("/insurances")
    ResponseEntity<?> newInsurance(@RequestBody Insurance newInsurance) {

        try{
            validator.insuranceRequiredFieldsValidation(newInsurance);
        } catch (InvalidException error){
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed") //
                            .withDetail(error.getMessage()));
        } catch (ParseException e) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed"));
        }

        List<CoveredGood> coveredGoodList = new ArrayList<>();

        newInsurance.getCoveredGoods().forEach(good -> {
            coveredGoodList.add(coveredGoodRepository.findById(good).orElseThrow(() -> new CoveredGoodNotFoundException(good)));
        });

        EntityModel<InsuranceJSONFormatter> entityModel = assembler.toModel(new InsuranceJSONFormatter(insuranceRepository.save(newInsurance), coveredGoodList));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/insurances/{policy}")
    ResponseEntity<?> replaceInsurance(@RequestBody Insurance newInsurance, @PathVariable Long policy) {

        try{
            validator.insuranceRequiredFieldsValidation(newInsurance);
        } catch (InvalidException error){
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed") //
                            .withDetail(error.getMessage()));
        } catch (ParseException e) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed"));
        }

        Insurance updatedInsurance = insuranceRepository.findById(policy) //
                .map(insurance -> {
                    insurance.setCpf(newInsurance.getCpf());
                    insurance.setStartDate(newInsurance.getStartDate());
                    insurance.setCoveredGoods(newInsurance.getCoveredGoods());
                    return insuranceRepository.save(insurance);
                }) //
                .orElseGet(() -> {
                    return insuranceRepository.save(newInsurance);
                });

        List<CoveredGood> coveredGoodList = new ArrayList<>();

        newInsurance.getCoveredGoods().forEach(good -> {
            coveredGoodList.add(coveredGoodRepository.findById(good).orElseThrow(() -> new CoveredGoodNotFoundException(good)));
        });

        EntityModel<InsuranceJSONFormatter> entityModel = assembler.toModel(new InsuranceJSONFormatter(updatedInsurance, coveredGoodList));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/insurances/{policy}")
    ResponseEntity<?> deleteInsurance(@PathVariable Long policy) {

        try {
            Insurance insurance = insuranceRepository.findById(policy) //
                    .orElseThrow(() -> new InsuranceNotFoundException(policy));

            insuranceRepository.deleteById(insurance.getPolicy());

            return ResponseEntity.ok().build();

        } catch(InsuranceNotFoundException error) {
            return ResponseEntity //
                    .status(HttpStatus.NOT_FOUND) //
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                    .body(Problem.create() //
                            .withTitle("Method not allowed") //
                            .withDetail("Insurance Not found"));
        }
    }
    @RequestMapping("/printTxt")
    ResponseEntity<?> PrintTxt() {
        txtBuilder.buildTxt(goodRepository.findAll(), insuranceRepository.findAll(), coveredGoodRepository.findAll());

        return ResponseEntity.ok().build();
    }
}