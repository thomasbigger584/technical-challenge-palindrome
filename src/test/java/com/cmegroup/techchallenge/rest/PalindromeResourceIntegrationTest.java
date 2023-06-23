package com.cmegroup.techchallenge.rest;


import com.cmegroup.techchallenge.TechChallengeApplication;
import com.cmegroup.techchallenge.dto.ErrorDTO;
import com.cmegroup.techchallenge.dto.PalindromeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = TechChallengeApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PalindromeResourceIntegrationTest {
    private static final TestRestTemplate REST_TEMPLATE = new TestRestTemplate();
    private static final String FILE_SYSTEM_LOCATION_PROPERTY = "app.fileSystem.fileLocation";

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        try {
            Path testFileLocation = Files.createTempDirectory(UUID.randomUUID().toString());
            registry.add(FILE_SYSTEM_LOCATION_PROPERTY, testFileLocation::toString);
        } catch (IOException e) {
            Assertions.fail("Failed to create temporary folder for testing", e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"madam", "kayak", ""})
    public void testGetPalindrome_IsSuccessful_ReturnsTrue(String value) {
        String url = createURLWithPort("/palindrome?value=" + value);

        ResponseEntity<PalindromeDTO> response =
                REST_TEMPLATE.getForEntity(url, PalindromeDTO.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), response.getStatusCode());

        PalindromeDTO palindrome = response.getBody();
        assertNotNull(palindrome);
        assertEquals(value, palindrome.getValue());
        assertTrue(palindrome.isPalindrome());
    }

    @ParameterizedTest
    @ValueSource(strings = {"palindrome", "technical", "challenge"})
    public void testGetPalindrome_IsSuccessful_ReturnsFalse(String value) {
        String url = createURLWithPort("/palindrome?value=" + value);

        ResponseEntity<PalindromeDTO> response =
                REST_TEMPLATE.getForEntity(url, PalindromeDTO.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), response.getStatusCode());

        PalindromeDTO palindrome = response.getBody();
        assertNotNull(palindrome);
        assertEquals(value, palindrome.getValue());
        assertFalse(palindrome.isPalindrome());
    }

    @ParameterizedTest
    @ValueSource(strings = {"madam1", "2kayak", "123", "ma1d2am"})
    public void testGetPalindrome_IsFailure_NoNumbersAllowed(String value) {
        String url = createURLWithPort("/palindrome?value=" + value);

        ResponseEntity<ErrorDTO> response =
                REST_TEMPLATE.getForEntity(url, ErrorDTO.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), response.getStatusCode());

        ErrorDTO error = response.getBody();
        assertNotNull(error);
        assertTrue(error.getMessage().contains("Palindrome value should not contain a number:"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"madam ", " kayak", "   ", "ma d am"})
    public void testGetPalindrome_IsFailure_NoSpacesAllowed(String value) {
        String url = createURLWithPort("/palindrome?value=" + value);

        ResponseEntity<ErrorDTO> response =
                REST_TEMPLATE.getForEntity(url, ErrorDTO.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), response.getStatusCode());

        ErrorDTO error = response.getBody();
        assertNotNull(error);
        assertTrue(error.getMessage().contains("Palindrome value should not contain any spaces"));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}