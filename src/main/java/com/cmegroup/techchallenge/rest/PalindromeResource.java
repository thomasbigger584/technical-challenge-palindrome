package com.cmegroup.techchallenge.rest;

import com.cmegroup.techchallenge.dto.PalindromeDTO;
import com.cmegroup.techchallenge.service.PalindromeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/palindrome")
public class PalindromeResource {
    private final PalindromeService service;

    public PalindromeResource(PalindromeService service) {
        this.service = service;
    }

    @GetMapping
    public PalindromeDTO isPalindrome(@RequestParam("value") String value) {
        return service.isPalindrome(value);
    }
}
