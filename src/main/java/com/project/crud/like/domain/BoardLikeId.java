package com.project.crud.like.domain;

import com.project.crud.account.domain.Account;
import com.project.crud.board.domain.Board;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLikeId implements Serializable {
    private Long boardId;
    private Long accountId;

    @Builder
    public BoardLikeId(final Long boardId, final Long accountId) {
        this.boardId = boardId;
        this.accountId = accountId;
    }
}
