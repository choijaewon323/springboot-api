package com.project.crud.account.domain;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    @DisplayName("account 유저네임 변경 시 username 글자 수 30글자 이상이면 IllegalStateException")
    @Test
    void updateUsernameTest() {
        // given
        Account account = Account.makeUser("acc", "pass");

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
        ThrowingCallable when = () -> Account.makeUser("1234567890123456789012345678901234567890", "1234");

        // then
        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("account 객체 생성 테스트 - username이 null일 시 IllegalStateException")
    @Test
    void ifWriterNullThrowsIllegalState() {
        // given
        // when
        ThrowingCallable when = () -> Account.makeUser(null, "password");

        // then
        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("account 객체 생성 테스트 - account가 blank일 시 IllegalStateException")
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n\n"})
    void ifWriterBlankThrowsIllegalState(String username) {
        // given
        // when
        ThrowingCallable when = () -> Account.makeUser(username, "password");

        // then
        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class);
    }
}
