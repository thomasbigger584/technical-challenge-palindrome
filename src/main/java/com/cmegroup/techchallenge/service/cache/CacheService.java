package com.cmegroup.techchallenge.service.cache;

import com.cmegroup.techchallenge.dto.PalindromeDTO;

import java.util.Optional;

public interface CacheService {
    Optional<PalindromeDTO> get(String key);

    void put(PalindromeDTO palindrome);
}
