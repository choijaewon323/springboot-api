package com.project.crud.security.dto;

import com.project.crud.account.domain.AccountRole;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTokenResponse {
    private String username;
    private String password;
    private AccountRole role;

    @Builder
    UserTokenResponse(String username, String password, AccountRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
