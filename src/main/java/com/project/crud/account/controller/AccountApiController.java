package com.project.crud.account.controller;

import com.project.crud.security.dto.UserTokenRequest;
import com.project.crud.account.service.AccountService;
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
    public ResponseEntity<Boolean> create(@RequestBody UserTokenRequest request) {
        Boolean result = accountService.create(request);

        if (result) {
            return ResponseEntity
                    .ok()
                    .body(result);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Boolean> findOne(@PathVariable String username) {
        return ResponseEntity.ok().build();
    }
}
