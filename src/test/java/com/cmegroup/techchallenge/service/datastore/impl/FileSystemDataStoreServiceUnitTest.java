package com.cmegroup.techchallenge.service.datastore.impl;

import com.cmegroup.techchallenge.dto.PalindromeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileSystemDataStoreServiceUnitTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String TEST_FILE_NAME = "palindromes.json";
    private static final String TEST_PALINDROME_1 = "madam";
    private static final String TEST_PALINDROME_2 = "kayak";

    @Mock
    private Environment environment;

    private FileSystemDataStoreService target;

    @BeforeEach
    void beforeEach() {
        target = new FileSystemDataStoreService(environment, OBJECT_MAPPER);
    }

    @Test
    void testGetAll_FileDoesNotExist(@TempDir Path tempDir) {
        when(environment.getProperty(anyString(), anyString())).thenReturn(tempDir.toString());

        List<PalindromeDTO> allPalindromes = target.getAll();
        assertEquals(0, allPalindromes.size());
    }

    @Test
    void testGetAll_FileExists(@TempDir Path tempDir) {
        when(environment.getProperty(anyString(), anyString())).thenReturn(tempDir.toString());

        File file = new File(tempDir.toFile(), TEST_FILE_NAME);
        PalindromeDTO palindrome = new PalindromeDTO(TEST_PALINDROME_1, true);
        List<PalindromeDTO> list = List.of(palindrome);

        writeListToFile(file, list);

        List<PalindromeDTO> allPalindromes = target.getAll();
        assertNotNull(allPalindromes);
        assertEquals(list.size(), allPalindromes.size());

        PalindromeDTO palindrome1 = list.get(0);
        assertEquals(palindrome.getValue(), palindrome1.getValue());
        assertEquals(palindrome.isPalindrome(), palindrome1.isPalindrome());
    }

    @Test
    void testSave_FileExists(@TempDir Path tempDir) {
        when(environment.getProperty(anyString(), anyString())).thenReturn(tempDir.toString());

        File file = new File(tempDir.toFile(), TEST_FILE_NAME);
        PalindromeDTO palindrome1 = new PalindromeDTO(TEST_PALINDROME_1, true);
        List<PalindromeDTO> list = List.of(palindrome1);
        writeListToFile(file, list);

        PalindromeDTO palindrome2 = new PalindromeDTO(TEST_PALINDROME_2, true);
        target.save(palindrome2);

        List<PalindromeDTO> allPalindromes = target.getAll();
        assertNotNull(allPalindromes);
        assertEquals(2, allPalindromes.size());

        assertEquals(palindrome1.getValue(), allPalindromes.get(0).getValue());
        assertEquals(palindrome1.isPalindrome(), allPalindromes.get(0).isPalindrome());

        assertEquals(palindrome2.getValue(), allPalindromes.get(1).getValue());
        assertEquals(palindrome2.isPalindrome(), allPalindromes.get(1).isPalindrome());
    }

    @Test
    void testSave_FileDoesNotExists(@TempDir Path tempDir) {
        when(environment.getProperty(anyString(), anyString())).thenReturn(tempDir.toString());

        PalindromeDTO palindrome1 = new PalindromeDTO(TEST_PALINDROME_2, true);
        target.save(palindrome1);

        List<PalindromeDTO> allPalindromes = target.getAll();
        assertNotNull(allPalindromes);
        assertEquals(1, allPalindromes.size());

        assertEquals(palindrome1.getValue(), allPalindromes.get(0).getValue());
        assertEquals(palindrome1.isPalindrome(), allPalindromes.get(0).isPalindrome());
    }

    private static void writeListToFile(File file, List<PalindromeDTO> list) {
        try {
            OBJECT_MAPPER.writeValue(file, list);
        } catch (IOException e) {
            fail("failed to write value to file during test", e);
        }
    }
}