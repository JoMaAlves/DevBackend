package com.project.av1.insurance;

class InsuranceNotFoundException extends RuntimeException {

    InsuranceNotFoundException(Long id) {
        super("Could not find insurance " + id);
    }
}