package com.project.crud.like.dto;

import com.project.crud.account.domain.Account;
import com.project.crud.board.domain.Board;
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
    BoardLikeRequestDto(final Long boardId, final String username) {
        this.boardId = boardId;
        this.username = username;
    }

    public BoardLike toEntity(final Account account, final Board board) {
        return BoardLike.builder()
                .account(account)
                .board(board)
                .build();
    }
}
