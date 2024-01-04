package com.project.crud.account.service;

import com.project.crud.account.domain.Account;
import com.project.crud.account.domain.AccountRepository;
import com.project.crud.security.dto.UserTokenRequest;
import com.project.crud.security.enums.AccountRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Account> findByUsername(UserTokenRequest request) {
        return accountRepository.findByUsername(request.getUsername());
    }

    @Override
    @Transactional
    public Boolean create(UserTokenRequest request) {
        Boolean isPresent = accountRepository.findByUsername(request.getUsername()).isPresent();

        if (isPresent) {
            return false;
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        accountRepository.save(new Account(request.getUsername(), encodedPassword, AccountRole.USER));

        return true;
    }
}
