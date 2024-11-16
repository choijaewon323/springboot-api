package com.project.crud.account.dto;

import com.project.crud.account.domain.Account;
import com.project.crud.account.domain.AccountRole;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    AccountRequestDto(final String username, final String password) {
        this.username = username;
        this.password = password;
    }
}
