package com.project.av1.insurance;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InsuranceNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(InsuranceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String insuranceNotFoundHandler(InsuranceNotFoundException ex) {
        return ex.getMessage();
    }
}