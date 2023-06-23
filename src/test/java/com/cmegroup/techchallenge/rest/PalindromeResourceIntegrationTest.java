package com.cmegroup.techchallenge.rest;


import com.cmegroup.techchallenge.TechChallengeApplication;
import com.cmegroup.techchallenge.dto.PalindromeDTO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TechChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PalindromeResourceIntegrationTest {
    private static final TestRestTemplate REST_TEMPLATE = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @ParameterizedTest
    @ValueSource(strings = {"madam", "kayak", ""})
    public void testGetPalindrome_IsSuccessful(String value) {
        String url = createURLWithPort("/palindrome?value=" + value);

        ResponseEntity<PalindromeDTO> response =
                REST_TEMPLATE.getForEntity(url, PalindromeDTO.class);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        PalindromeDTO palindrome = response.getBody();
        assertNotNull(palindrome);
        assertEquals(value, palindrome.getValue());
        assertTrue(palindrome.isPalindrome());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}