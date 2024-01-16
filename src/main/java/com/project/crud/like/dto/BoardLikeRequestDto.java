package com.project.crud.like.dto;

import com.project.crud.like.domain.BoardLike;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardLikeRequestDto {
    @NotNull
    private Long boardId;
    @NotBlank
    private String username;

    @Builder
    BoardLikeRequestDto(Long boardId, String username) {
        this.boardId = boardId;
        this.username = username;
    }
}
