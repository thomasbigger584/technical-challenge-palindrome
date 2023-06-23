package com.cmegroup.techchallenge.service.datastore.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileSystemDataStoreServiceTest {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private FileSystemDataStoreService target;

    @BeforeEach
    public void beforeEach() {
        this.target = new FileSystemDataStoreService(objectMapper);
    }
}