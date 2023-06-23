package com.cmegroup.techchallenge.service.validation.exception;

import com.cmegroup.techchallenge.exception.PalindromeAppException;

public class ValidationException extends PalindromeAppException {

    public ValidationException(String message) {
        super(message);
    }
}
