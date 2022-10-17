package com.project.av1.coveredGood;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class CoveredGoodNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CoveredGoodNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String goodNotFoundHandler(CoveredGoodNotFoundException ex) {
        return ex.getMessage();
    }
}