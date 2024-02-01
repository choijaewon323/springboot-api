package com.project.crud.security.token;

import com.project.crud.account.domain.AccountRole;
import com.project.crud.security.dto.UserTokenRequest;
import com.project.crud.security.dto.UserTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenUtilsTests {

    UserTokenResponse userToken;

    @BeforeEach
    void init() {
        userToken = UserTokenResponse.builder()
                .username("jaewon")
                .password("password123")
                .role(AccountRole.USER)
                .build();
    }

    @DisplayName("토큰 발부 테스트")
    @Test
    void createTokenTest() {
        // when
        final String token = TokenUtils.generateJwtToken(userToken);

        // then
        System.out.println(token);
    }

    @DisplayName("토큰에서 username 추출하기")
    @Test
    void getUsernameFromTokenTest() {
        // given
        final String token = TokenUtils.generateJwtToken(userToken);

        // when
        final String username = TokenUtils.getUsernameFromToken(token);

        // then
        assertThat(username).isEqualTo("jaewon");
    }

    @DisplayName("토큰에서 role 추출하기")
    @Test
    void getRoleFromTokenTest() {
        // given
        final String token = TokenUtils.generateJwtToken(userToken);

        // when
        final AccountRole role = TokenUtils.getRoleFromToken(token);

        // then
        assertThat(role).isEqualTo(AccountRole.USER);
    }

    @DisplayName("토큰 유효성 검사")
    @Test
    void isValidTokenTest() {
        // given
        final String token = TokenUtils.generateJwtToken(userToken);

        // when
        final boolean result = TokenUtils.isValidToken(token);

        // then
        assertThat(result).isTrue();
    }
}
