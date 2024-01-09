package com.project.crud.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountUsernameUpdateDto {
    @NotBlank
    private String before;
    @NotBlank
    private String after;
}
