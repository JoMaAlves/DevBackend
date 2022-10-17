package com.project.av1.good;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GoodNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(GoodNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String goodNotFoundHandler(GoodNotFoundException ex) {
        return ex.getMessage();
    }
}