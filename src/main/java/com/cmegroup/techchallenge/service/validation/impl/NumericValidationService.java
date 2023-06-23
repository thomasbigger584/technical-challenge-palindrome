package com.cmegroup.techchallenge.service.validation.impl;

import com.cmegroup.techchallenge.service.validation.ValidationService;
import com.cmegroup.techchallenge.service.validation.exception.ValidationException;
import org.springframework.stereotype.Service;

@Service("numeric")
public class NumericValidationService implements ValidationService {
    @Override
    public void validate(String value) {
        char[] characterArray = value.toCharArray();
        for (char character : characterArray) {
            if (Character.isDigit(character)) {
                throw new ValidationException("Palindrome value should not contain a number: " + character);
            }
        }
    }
}
