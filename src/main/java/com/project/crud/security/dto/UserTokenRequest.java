package com.project.crud.security.dto;


import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTokenRequest {
    private String username;
    private String password;

    @Builder
    UserTokenRequest(final String username, final String password) {
        this.username = username;
        this.password = password;
    }
}
