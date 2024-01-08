package com.project.crud.account.controller;

import com.project.crud.account.dto.AccountRequestDto;
import com.project.crud.security.dto.UserTokenRequest;
import com.project.crud.account.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountApiController {

    private final AccountService accountService;

    public AccountApiController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Boolean> create(@Valid @RequestBody AccountRequestDto request) {
        accountService.create(request);

        return ResponseEntity
                .ok()
                .build();
    }
}
