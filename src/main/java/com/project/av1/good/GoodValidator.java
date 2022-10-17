package com.project.av1.good;

import com.project.av1.InvalidException;
import org.springframework.stereotype.Service;

@Service
public class GoodValidator {

    public void goodRequiredFieldsValidation(Good good) throws InvalidException {
        if(good.getName().isBlank() || !(good.getName().length() > 0 && good.getName().length() <= 40)){
            throw new InvalidException("Not a valid name received!");
        }

        if(good.getExpectedValue() == null || good.getExpectedValue() <= 0){
            throw new InvalidException("Not a valid expectedValue received!");
        }

        if(good.getAliquot() == null || !(good.getAliquot() >= 0 && good.getAliquot() <= 1)){
            throw new InvalidException("Not a valid aliquot received!");
        }
    }
}
