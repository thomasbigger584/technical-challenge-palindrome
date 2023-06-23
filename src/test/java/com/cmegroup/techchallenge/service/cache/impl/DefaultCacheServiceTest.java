package com.cmegroup.techchallenge.service.cache.impl;

import com.cmegroup.techchallenge.service.datastore.DataStoreService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;

@ExtendWith(MockitoExtension.class)
class DefaultCacheServiceTest {

    @Mock
    private Cache cache;

    @Mock
    private DataStoreService dataStoreService;

    @InjectMocks
    private DefaultCacheService target;


}