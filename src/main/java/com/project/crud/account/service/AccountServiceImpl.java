package com.project.crud.account.service;

import com.project.crud.account.domain.Account;
import com.project.crud.account.dto.AccountRequestDto;
import com.project.crud.account.dto.AccountResponseDto;
import com.project.crud.account.exception.AccountAlreadyExistException;
import com.project.crud.account.repository.AccountRepository;
import com.project.crud.security.dto.UserTokenRequest;
import com.project.crud.account.domain.AccountRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void create(AccountRequestDto request) {
        Boolean isPresent = accountRepository.findByUsername(request.getUsername()).isPresent();

        if (isPresent) {
            throw new AccountAlreadyExistException("이미 있는 계정입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        accountRepository.save(new Account(request.getUsername(), encodedPassword, AccountRole.USER));
    }

    @Override
    public List<AccountResponseDto> readAll() {
        return null;
    }

    @Override
    public AccountResponseDto readOne(Long accountId) {
        return null;
    }

    @Override
    public void updateUsername(String username) {

    }

    @Override
    public void updatePassword(AccountRequestDto dto) {

    }

    @Override
    public void delete(String username) {

    }
}
