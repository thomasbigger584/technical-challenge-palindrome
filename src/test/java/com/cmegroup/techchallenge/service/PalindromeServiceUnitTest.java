package com.cmegroup.techchallenge.service;

import com.cmegroup.techchallenge.dto.PalindromeDTO;
import com.cmegroup.techchallenge.service.cache.CacheService;
import com.cmegroup.techchallenge.service.datastore.DataStoreService;
import com.cmegroup.techchallenge.service.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PalindromeServiceUnitTest {
    private static final String TEST_PALINDROME_1 = "madam";
    private static final String TEST_PALINDROME_2 = "palindrome";

    @Mock
    private ValidationService validationService;

    @Mock
    private CacheService cacheService;

    @Mock
    private DataStoreService dataStoreService;

    private PalindromeService target;

    @BeforeEach
    void beforeEach() {
        List<ValidationService> validationList = List.of(validationService);
        target = new PalindromeService(validationList, cacheService, dataStoreService);
    }

    @Test
    void testIsPalindrome_NotInCache_IsTrue() {
        when(cacheService.get(TEST_PALINDROME_1)).thenReturn(Optional.empty());

        PalindromeDTO result = target.isPalindrome(TEST_PALINDROME_1);
        assertTrue(result.isPalindrome());

        verify(validationService, times(1)).validate(TEST_PALINDROME_1);
        verify(cacheService, times(1)).put(result);
        verify(dataStoreService, times(1)).save(result);
    }

    @Test
    void testIsPalindrome_NotInCache_IsFalse() {
        when(cacheService.get(TEST_PALINDROME_2)).thenReturn(Optional.empty());

        PalindromeDTO result = target.isPalindrome(TEST_PALINDROME_2);
        assertFalse(result.isPalindrome());

        verify(validationService, times(1)).validate(TEST_PALINDROME_2);
        verify(cacheService, times(1)).put(result);
        verify(dataStoreService, times(1)).save(result);
    }

    @Test
    void testIsPalindrome_InCache() {
        PalindromeDTO palindrome = new PalindromeDTO();
        palindrome.setValue(TEST_PALINDROME_1);
        palindrome.setPalindrome(true);
        when(cacheService.get(TEST_PALINDROME_1)).thenReturn(Optional.of(palindrome));

        PalindromeDTO result = target.isPalindrome(TEST_PALINDROME_1);

        assertEquals(palindrome, result);

        verify(validationService, times(1)).validate(TEST_PALINDROME_1);
        verify(cacheService, times(0)).put(result);
        verify(dataStoreService, times(0)).save(result);
    }
}