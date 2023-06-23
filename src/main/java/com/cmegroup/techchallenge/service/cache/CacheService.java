package com.cmegroup.techchallenge.service.cache;

import com.cmegroup.techchallenge.dto.PalindromeDTO;

import java.util.Optional;

/**
 * Extension point to handle caching of Palindromes
 */
public interface CacheService {

    /**
     * Get a palindrome from cache if it exists.
     *
     * @param key the key to which the palindrome was stored against. This is the provided value from the user
     * @return an Optional which will have the Palindrome present if it exists, it will be empty otherwise.
     */
    Optional<PalindromeDTO> get(String key);

    /**
     * Put the palindrome into cache. If it exists then it will overwrite
     *
     * @param palindrome the palindrome to be inserted into the cache
     */
    void put(PalindromeDTO palindrome);
}
