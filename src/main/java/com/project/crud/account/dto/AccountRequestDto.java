package com.project.crud.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
