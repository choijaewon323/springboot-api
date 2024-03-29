package com.project.crud.account.repository;

import com.project.crud.account.domain.Account;
import com.project.crud.account.domain.AccountRole;
import com.project.crud.config.QueryDSLConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(QueryDSLConfig.class)
@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void init() {
        makeAccountsByCount(1, 2);
    }

    @DisplayName("username 기준 조회 테스트")
    @Test
    void findByUsernameTest() {
        // when
        Account account = accountRepository.findByUsername("유저네임1")
                .get();

        // then
        assertThat(account.getUsername()).isEqualTo("유저네임1");
    }

    @DisplayName("role 기준 조회 테스트")
    @Test
    void findAccountsByRoleTest() {
        // when
        List<Account> accounts = accountRepository.findByRole(AccountRole.USER);

        // then
        assertThat(accounts.size()).isEqualTo(2);
        assertThat(accounts.get(0).getRole()).isEqualTo(AccountRole.USER);
        assertThat(accounts.stream().anyMatch(e -> {
            if (e.getUsername().equals("유저네임1")) {
                return true;
            }
            return false;
        })).isTrue();
    }

    private void makeAccount(final int number) {
        accountRepository.save(Account.builder()
                        .username("유저네임" + number)
                        .password("비밀번호" + number)
                        .role(AccountRole.USER)
                    .build());
    }

    private void makeAccountsByCount(final int startNumber, final int count) {
        for (int i = startNumber; i < startNumber + count; i++) {
            makeAccount(i);
        }
    }
}
