package com.cmegroup.techchallenge.service.datastore.exception;

import com.cmegroup.techchallenge.exception.PalindromeAppException;

public class DataStoreException extends PalindromeAppException {

    public DataStoreException(String message, Throwable t) {
        super(message, t);
    }
}
