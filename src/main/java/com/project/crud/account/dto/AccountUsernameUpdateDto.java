package com.project.crud.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AccountUsernameUpdateDto {
    @NotBlank
    private String before;
    @NotBlank
    private String after;

    @Builder
    AccountUsernameUpdateDto(final String before, final String after) {
        this.before = before;
        this.after = after;
    }
}
