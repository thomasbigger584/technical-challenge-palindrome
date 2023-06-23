package com.cmegroup.techchallenge.service.datastore.impl;

import com.cmegroup.techchallenge.dto.PalindromeDTO;
import com.cmegroup.techchallenge.service.datastore.DataStoreService;
import com.cmegroup.techchallenge.service.datastore.exception.DataStoreException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("fileSystem")
@ConditionalOnProperty(name = "app.datastore-type", havingValue = "fileSystem")
public class FileSystemDataStoreService implements DataStoreService {
    private static final String DIRECTORY_PROPERTY = "user.home";
    private static final String PALINDROME_JSON_FILE_NAME = "palindromes";
    private static final String FILE_PATH = String.format("%s/%s.json",
            System.getProperty(DIRECTORY_PROPERTY), PALINDROME_JSON_FILE_NAME);
    private static final File FILE = new File(FILE_PATH);
    private static final Object LOCK = new Object();
    private final Logger logger = LoggerFactory.getLogger(FileSystemDataStoreService.class);
    private final ObjectMapper objectMapper;

    public FileSystemDataStoreService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<PalindromeDTO> getAll() {
        synchronized (LOCK) {
            if (!FILE.exists()) {
                return new ArrayList<>();
            }
            if (FILE.length() == 0) {
                return new ArrayList<>();
            }
            try {
                return objectMapper.readValue(FILE, new TypeReference<>() {
                });
            } catch (IOException e) {
                throw new DataStoreException("Failed to read from palindromes file at path: " + FILE_PATH, e);
            }
        }
    }

    @Override
    public void save(PalindromeDTO palindrome) {
        logger.info("Saving palindrome {}", palindrome);

        synchronized (LOCK) {
            ensureFileCreated();

            List<PalindromeDTO> allPalindromes = getAll();
            allPalindromes.add(palindrome);

            try {
                objectMapper.writeValue(FILE, allPalindromes);
            } catch (IOException e) {
                throw new DataStoreException("Failed to write to palindromes file at path: " + FILE_PATH, e);
            }
        }
    }

    private synchronized void ensureFileCreated() {
        if (!FILE.exists()) {
            try {
                boolean newFile = FILE.createNewFile();
                if (!newFile) {
                    logger.warn("File already created at path: " + FILE_PATH);
                }
            } catch (IOException e) {
                throw new DataStoreException("Failed to create new palindromes file at path: " + FILE_PATH, e);
            }
        }
    }
}
