package com.project.crud.account.dto;

import com.project.crud.account.domain.Account;
import com.project.crud.account.domain.AccountRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountResponseDto {
    @NotNull
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private AccountRole role;

    @Builder
    AccountResponseDto(final Long id, final String username, final String password, final AccountRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static AccountResponseDto toDto(Account account) {
        return AccountResponseDto.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .role(account.getRole())
                .build();
    }
}
