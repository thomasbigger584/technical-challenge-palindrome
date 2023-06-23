package com.cmegroup.techchallenge.service.validation.impl;

import com.cmegroup.techchallenge.service.validation.exception.ValidationException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class SpaceValidationServiceUnitTest {

    @InjectMocks
    private SpaceValidationService target;

    @ParameterizedTest
    @ValueSource(strings = {"madam", "kayak", ""})
    public void testValidate_IsSuccessful(String value) {
        target.validate(value);
        assertTrue(true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"madam ", " kayak", "   ", "ma d am"})
    public void testValidate_IsFailure(String value) {
        assertThrows(ValidationException.class, () -> target.validate(value));
    }
}