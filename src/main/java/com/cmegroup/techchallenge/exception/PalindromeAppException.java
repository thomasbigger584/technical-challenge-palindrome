package com.cmegroup.techchallenge.exception;

public class PalindromeAppException extends RuntimeException {

    public PalindromeAppException(String message) {
        super(message);
    }

    public PalindromeAppException(String message, Throwable t) {
        super(message, t);
    }
}
