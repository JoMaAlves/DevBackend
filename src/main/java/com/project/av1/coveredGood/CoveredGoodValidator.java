package com.project.av1.coveredGood;

import com.project.av1.InvalidException;
import com.project.av1.good.Good;
import org.springframework.stereotype.Service;

@Service
public class CoveredGoodValidator {

    public void coveredGoodRequiredFieldsValidation(CoveredGood coveredGood, Good good) throws InvalidException {
        if(good == null) {
            throw new InvalidException("Not a valid Good Code received!");
        }

       if(coveredGood.getDeadline() == null || coveredGood.getDeadline() < 0){
            throw new InvalidException("Not a valid Deadline received!");
        }

        if(!(coveredGood.getRiskFactor() != null && coveredGood.getRiskFactor() > 1)){
            throw new InvalidException("Not a valid RiskFactor received!");
        }
    }
}
