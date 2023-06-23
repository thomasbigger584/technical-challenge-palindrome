package com.cmegroup.techchallenge.service.datastore.impl;

import com.cmegroup.techchallenge.dto.PalindromeDTO;
import com.cmegroup.techchallenge.service.datastore.DataStoreService;
import com.cmegroup.techchallenge.service.datastore.exception.DataStoreException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("fileSystem")
@ConditionalOnProperty(name = "app.datastore-type", havingValue = "fileSystem")
public class FileSystemDataStoreService implements DataStoreService {
    private static final String DEFAULT_DIRECTORY_PROPERTY = "user.home";
    private static final String DEFAULT_OUTPUT_DIRECTORY = System.getProperty(DEFAULT_DIRECTORY_PROPERTY);
    private static final String PALINDROME_JSON_FILE_NAME = "palindromes";
    private static final String FILE_LOCATION_PROPERTY = "app.fileSystem.fileLocation";
    private static final String FILE_PATH_FORMAT = "%s/%s.json";
    private static final Object LOCK = new Object();
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemDataStoreService.class);
    private final Environment environment;
    private final ObjectMapper objectMapper;

    public FileSystemDataStoreService(Environment environment, ObjectMapper objectMapper) {
        this.environment = environment;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<PalindromeDTO> getAll() {
        File file = getFile();

        synchronized (LOCK) {
            if (!file.exists()) {
                return new ArrayList<>();
            }
            if (file.length() == 0) {
                return new ArrayList<>();
            }
            try {
                return objectMapper.readValue(file, new TypeReference<>() {
                });
            } catch (IOException e) {
                throw new DataStoreException("Failed to read from palindromes file at path: " + file.getPath(), e);
            }
        }
    }

    @Override
    public void save(PalindromeDTO palindrome) {
        LOGGER.info("Saving palindrome {}", palindrome);
        File file = getFile();

        synchronized (LOCK) {
            ensureFileCreated(file);

            List<PalindromeDTO> allPalindromes = getAll();
            allPalindromes.add(palindrome);

            try {
                objectMapper.writeValue(file, allPalindromes);
            } catch (IOException e) {
                throw new DataStoreException("Failed to write to palindromes file at path: " + file.getPath(), e);
            }
        }
    }

    private synchronized void ensureFileCreated(File file) {
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    LOGGER.warn("File already created at path: " + file.getPath());
                }
            } catch (IOException e) {
                throw new DataStoreException("Failed to create new palindromes file at path: " + file.getPath(), e);
            }
        }
    }

    private File getFile() {
        String fileLocation = environment.getProperty(FILE_LOCATION_PROPERTY, DEFAULT_OUTPUT_DIRECTORY);
        return new File(String.format(FILE_PATH_FORMAT, fileLocation, PALINDROME_JSON_FILE_NAME));
    }
}
