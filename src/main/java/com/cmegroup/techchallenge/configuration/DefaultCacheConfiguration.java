package com.cmegroup.techchallenge.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ConditionalOnProperty(name = "app.cache-type", havingValue = "default")
public class DefaultCacheConfiguration {
    private static final String CACHE_NAME = "palindromes";

    @Bean
    public CacheManager palindromesCacheManager() {
        return new ConcurrentMapCacheManager(CACHE_NAME);
    }

    @Bean
    public Cache palindromesCache(CacheManager cacheManager) {
        return cacheManager.getCache(CACHE_NAME);
    }
}
