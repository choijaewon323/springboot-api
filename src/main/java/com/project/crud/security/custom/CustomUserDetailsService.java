package com.project.crud.security.custom;

import com.project.crud.account.domain.Account;
import com.project.crud.account.domain.AccountRepository;
import com.project.crud.security.dto.UserTokenResponse;
import com.project.crud.security.exception.UserNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return new CustomUserDetails(
                new UserTokenResponse(account.getUsername(), account.getPassword(), account.getRole()),
                Collections.singleton(new SimpleGrantedAuthority(account.getRole().name()))
        );
    }
}
