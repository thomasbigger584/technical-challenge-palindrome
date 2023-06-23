package com.cmegroup.techchallenge.service.datastore;

import com.cmegroup.techchallenge.dto.PalindromeDTO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * Extension point to handle storing palindromes in a permanent persistence store
 */
public interface DataStoreService {

    /**
     * Get all palindromes from the datastore
     *
     * @return a list of palindromes stored in permanent persistence
     */
    List<PalindromeDTO> getAll();

    /**
     * Save the palindrome into the datastore
     *
     * @param palindrome the palindrome to store into the permanent persistence
     */
    @Async
    void save(PalindromeDTO palindrome);
}
