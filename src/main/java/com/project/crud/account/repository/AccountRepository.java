package com.project.crud.account.repository;

import com.project.crud.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsernameAndPassword(String username, String password);

    Optional<Account> findByUsername(String username);
}
