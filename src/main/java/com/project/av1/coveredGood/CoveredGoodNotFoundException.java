package com.project.av1.coveredGood;

public class CoveredGoodNotFoundException extends RuntimeException {

    public CoveredGoodNotFoundException(Long code) {
        super("Could not find good " + code);
    }
}