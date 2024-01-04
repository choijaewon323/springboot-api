package com.project.crud.account.service;

import com.project.crud.account.domain.Account;
import com.project.crud.security.dto.UserTokenRequest;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findByUsername(UserTokenRequest request);
    Boolean create(UserTokenRequest request);
}
