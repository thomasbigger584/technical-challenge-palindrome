package com.cmegroup.techchallenge.service.datastore;

import com.cmegroup.techchallenge.dto.PalindromeDTO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface DataStoreService {
    List<PalindromeDTO> getAll();
    @Async
    void save(PalindromeDTO palindrome);
}
