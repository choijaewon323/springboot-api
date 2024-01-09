package com.project.crud.account.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class AccountTest {

    Account account;

    @BeforeEach
    void init() {
        account = new Account("유저네임", "비밀번호", AccountRole.USER);
    }

    @DisplayName("유저네임 변경 테스트")
    @Test
    void updateUsernameTest() {
        // when
        account.updateUsername("유저네임23");

        // then
        assertThat(account.getUsername()).isEqualTo("유저네임23");
    }

    @DisplayName("비밀번호 변경 테스트")
    @Test
    void updatePasswordTest() {
        // when
        account.updatePassword("비밀번호321");

        // then
        assertThat(account.getPassword()).isEqualTo("비밀번호321");
    }
}
