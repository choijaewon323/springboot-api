package com.project.crud.like.dto;

import com.project.crud.like.domain.BoardLike;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardLikeRequestDto {
    @NotNull
    private Long boardId;
    @NotBlank
    private String username;
}
