package com.cmegroup.techchallenge.exception;

import com.cmegroup.techchallenge.dto.ErrorDTO;
import com.cmegroup.techchallenge.service.validation.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<ErrorDTO> handleException(ValidationException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorDTO> handleException(Exception exception) {
        return ResponseEntity
                .internalServerError()
                .body(new ErrorDTO(exception.getMessage()));
    }
}
