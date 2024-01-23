package com.project.crud.account.controller;

import com.project.crud.account.dto.AccountRequestDto;
import com.project.crud.account.dto.AccountUsernameUpdateDto;
import com.project.crud.security.dto.UserTokenRequest;
import com.project.crud.account.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.crud.common.ApiResponse.ok;

@RestController
@RequestMapping("/api/v1/account")
public class AccountApiController {

    private final AccountService accountService;

    public AccountApiController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody AccountRequestDto request) {
        accountService.create(request);

        return ok();
    }

    @PutMapping("/username")
    public ResponseEntity<Void> updateUsername(@RequestBody @Valid AccountUsernameUpdateDto dto) {
        accountService.updateUsername(dto);

        return ok();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid AccountRequestDto dto) {
        accountService.updatePassword(dto);

        return ok();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(@NotBlank @PathVariable String username) {
        accountService.delete(username);

        return ok();
    }
}
