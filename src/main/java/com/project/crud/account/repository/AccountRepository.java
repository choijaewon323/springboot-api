package com.project.crud.account.repository;

import com.project.crud.account.domain.Account;
import com.project.crud.account.domain.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsernameAndPassword(String username, String password);

    Optional<Account> findByUsername(String username);

    @Query("select account from Account account where account.role = :role")
    List<Account> findAccountsByRole(@Param("role") AccountRole role);
}
