package com.project.av1.good;

class GoodNotFoundException extends RuntimeException {

    GoodNotFoundException(Long code) {
        super("Could not find good " + code);
    }
}