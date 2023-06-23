package com.cmegroup.techchallenge.service.cache.impl;

import com.cmegroup.techchallenge.dto.PalindromeDTO;
import com.cmegroup.techchallenge.service.datastore.DataStoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultCacheServiceUnitTest {
    private static final String TEST_VALUE = "madam";

    @Mock
    private Cache cache;

    @Mock
    private DataStoreService dataStoreService;

    @InjectMocks
    private DefaultCacheService target;

    @Test
    void testInit() {
        PalindromeDTO palindrome = getPalindrome();
        List<PalindromeDTO> palindroneList = List.of(palindrome, palindrome);

        when(dataStoreService.getAll()).thenReturn(palindroneList);

        target.init();

        verify(cache, times(palindroneList.size())).put(palindrome.getValue(), palindrome);
    }

    @Test
    void testPut() {
        PalindromeDTO palindrome = getPalindrome();

        target.put(palindrome);

        verify(cache, times(1)).put(palindrome.getValue(), palindrome);
    }

    @Test
    void testGet_IsPresent() {
        PalindromeDTO palindrome = getPalindrome();

        when(cache.get(TEST_VALUE, PalindromeDTO.class)).thenReturn(palindrome);

        Optional<PalindromeDTO> palindromeOpt = target.get(TEST_VALUE);
        assertTrue(palindromeOpt.isPresent());

        PalindromeDTO responsePalindrome = palindromeOpt.get();
        assertEquals(palindrome, responsePalindrome);
    }

    @Test
    void testGet_IsNotPresent() {
        Optional<PalindromeDTO> palindromeOpt = target.get(TEST_VALUE);
        assertFalse(palindromeOpt.isPresent());
    }

    private PalindromeDTO getPalindrome() {
        PalindromeDTO palindrome = new PalindromeDTO();
        palindrome.setValue(TEST_VALUE);
        palindrome.setPalindrome(true);
        return palindrome;
    }
}