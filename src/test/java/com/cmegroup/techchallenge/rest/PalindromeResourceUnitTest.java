package com.cmegroup.techchallenge.rest;


import com.cmegroup.techchallenge.dto.PalindromeDTO;
import com.cmegroup.techchallenge.service.PalindromeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PalindromeResourceUnitTest {
    private static final String TEST_VALUE = "madam";

    @Mock
    private PalindromeService palindromeService;

    @InjectMocks
    private PalindromeResource target;

    @Test
    void testIsPalindrome() {
        PalindromeDTO palindrome = new PalindromeDTO();
        palindrome.setValue(TEST_VALUE);
        palindrome.setPalindrome(true);

        when(palindromeService.isPalindrome(TEST_VALUE)).thenReturn(palindrome);

        PalindromeDTO responsePalindrome = target.isPalindrome(TEST_VALUE);

        assertEquals(palindrome, responsePalindrome);
    }
}