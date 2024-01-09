package com.project.crud.account.service;

import com.project.crud.account.domain.Account;
import com.project.crud.account.dto.AccountRequestDto;
import com.project.crud.account.dto.AccountResponseDto;
import com.project.crud.account.dto.AccountUsernameUpdateDto;
import com.project.crud.security.dto.UserTokenRequest;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    void create(AccountRequestDto dto);
    List<AccountResponseDto> readAll();
    AccountResponseDto readOne(Long accountId);
    void updateUsername(AccountUsernameUpdateDto dto);
    void updatePassword(AccountRequestDto dto);
    void delete(String username);
}
