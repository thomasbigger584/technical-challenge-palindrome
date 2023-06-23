package com.cmegroup.techchallenge.service.cache.impl;

import com.cmegroup.techchallenge.configuration.DefaultCacheConfiguration;
import com.cmegroup.techchallenge.dto.PalindromeDTO;
import com.cmegroup.techchallenge.service.cache.CacheService;
import com.cmegroup.techchallenge.service.datastore.DataStoreService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("default")
@ConditionalOnBean(DefaultCacheConfiguration.class)
public class DefaultCacheService implements CacheService {
    private final Cache cache;
    private final DataStoreService dataStoreService;

    public DefaultCacheService(Cache cache,
                               DataStoreService dataStoreService) {
        this.cache = cache;
        this.dataStoreService = dataStoreService;
    }

    @PostConstruct
    public void init() {
        List<PalindromeDTO> allPalindromes = dataStoreService.getAll();
        for (PalindromeDTO palindrome : allPalindromes) {
            put(palindrome);
        }
    }

    @Override
    public void put(PalindromeDTO palindrome) {
        cache.put(palindrome.getValue(), palindrome);
    }

    @Override
    public Optional<PalindromeDTO> get(String key) {
        PalindromeDTO palindrome = cache.get(key, PalindromeDTO.class);
        return Optional.ofNullable(palindrome);
    }
}
