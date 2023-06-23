package com.cmegroup.techchallenge.service;

import com.cmegroup.techchallenge.service.cache.CacheService;
import com.cmegroup.techchallenge.service.datastore.DataStoreService;
import com.cmegroup.techchallenge.service.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PalindromeServiceTest {

    @Mock
    private ValidationService validationService;

    @Mock
    private CacheService cacheService;

    @Mock
    private DataStoreService dataStoreService;

    private PalindromeService target;

    @BeforeEach
    public void beforeEach() {
        List<ValidationService> validationList = List.of(validationService);
        target = new PalindromeService(validationList, cacheService, dataStoreService);
    }


}