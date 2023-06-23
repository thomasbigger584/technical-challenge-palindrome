package com.cmegroup.techchallenge.service.datastore.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
class FileSystemDataStoreServiceUnitTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Environment environment;

    @InjectMocks
    private FileSystemDataStoreService target;

}