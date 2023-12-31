package com.cmegroup.techchallenge.dto;

public class ErrorDTO {
    private final String message;

    public ErrorDTO() {
        this.message = null;
    }

    public ErrorDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
