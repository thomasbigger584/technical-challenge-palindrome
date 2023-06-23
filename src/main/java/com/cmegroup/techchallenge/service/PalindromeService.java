package com.cmegroup.techchallenge.service;

import com.cmegroup.techchallenge.dto.PalindromeDTO;
import com.cmegroup.techchallenge.service.cache.CacheService;
import com.cmegroup.techchallenge.service.datastore.DataStoreService;
import com.cmegroup.techchallenge.service.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PalindromeService {
    private final List<ValidationService> validationList;
    private final CacheService cacheService;
    private final DataStoreService dataStoreService;

    public PalindromeService(List<ValidationService> validationList,
                             CacheService cacheService,
                             DataStoreService dataStoreService) {
        this.validationList = validationList;
        this.cacheService = cacheService;
        this.dataStoreService = dataStoreService;
    }

    public PalindromeDTO isPalindrome(String value) {
        validate(value);

        Optional<PalindromeDTO> cacheOptional = cacheService.get(value);
        if (cacheOptional.isPresent()) {
            return cacheOptional.get();
        }

        boolean result = calculateIsPalindrome(value);
        PalindromeDTO palindrome = new PalindromeDTO(value, result);

        save(palindrome);

        return palindrome;
    }

    private void validate(String value) {
        for (ValidationService validationService : validationList) {
            validationService.validate(value);
        }
    }

    private boolean calculateIsPalindrome(String value) {
        StringBuilder valueStringBuilder = new StringBuilder(value);
        valueStringBuilder.reverse();
        String reversedValue = valueStringBuilder.toString();
        return value.equals(reversedValue);
    }

    private void save(PalindromeDTO palindrome) {
        cacheService.put(palindrome);
        dataStoreService.save(palindrome);
    }
}
