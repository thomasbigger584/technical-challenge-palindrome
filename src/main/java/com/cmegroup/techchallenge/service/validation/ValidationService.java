package com.cmegroup.techchallenge.service.validation;

/**
 * Extension point to handle different types of validation
 */
public interface ValidationService {

    /**
     * Validate the provided value. If it doesnt throw an exception then it is considered valid
     *
     * @param value the input value under validation
     * @throws com.cmegroup.techchallenge.service.validation.exception.ValidationException when there is a validation error
     */
    void validate(String value);
}
