package com.project.crud.account.domain;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    @DisplayName("account 유저네임 변경 시 username 글자 수 30글자 이상이면 IllegalStateException")
    @Test
    void updateUsernameTest() {
        // given
        Account account = Account.builder()
                .username("유저네임")
                .password("1234")
                .role(AccountRole.USER)
                .build();

        // when
        ThrowingCallable when = () -> account.updateUsername("1234567890123456789012345678901234567890");

        // then
        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("account 생성 시 username이 30자 이상인 경우 IllegalStateException")
    @Test
    void checkUsernameLengthWhenCreated() {
        // given
        // when
        ThrowingCallable when = () -> {
            Account.builder()
                    .username("1234567890123456789012345678901234567890")
                    .password("1234")
                    .role(AccountRole.USER)
                    .build();
        };

        // then
        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class);
    }
}
