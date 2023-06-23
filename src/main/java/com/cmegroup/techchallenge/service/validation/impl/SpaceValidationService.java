package com.cmegroup.techchallenge.service.validation.impl;

import com.cmegroup.techchallenge.service.validation.exception.ValidationException;
import com.cmegroup.techchallenge.service.validation.ValidationService;
import org.springframework.stereotype.Service;

@Service("space")
public class SpaceValidationService implements ValidationService {

    @Override
    public void validate(String value) {
        if (value.contains(" ")) {
            throw new ValidationException("Palindrome value should not contain any spaces");
        }
    }
}
