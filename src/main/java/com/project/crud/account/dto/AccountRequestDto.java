package com.project.crud.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @Builder
    AccountRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
