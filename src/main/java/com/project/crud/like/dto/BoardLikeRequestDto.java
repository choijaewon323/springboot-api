package com.project.crud.like.dto;

import com.project.crud.like.domain.BoardLike;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BoardLikeRequestDto {
    @NotNull
    private Long boardId;
    @NotBlank
    private String username;

    public BoardLike toEntity(final Long accountId) {
        return BoardLike.of(boardId, accountId);
    }
}
