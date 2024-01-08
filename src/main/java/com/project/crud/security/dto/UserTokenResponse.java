package com.project.crud.security.dto;

import com.project.crud.account.domain.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenResponse {
    private String username;
    private String password;
    private AccountRole role;
}
