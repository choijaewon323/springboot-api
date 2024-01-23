package com.project.crud.like.domain;

import com.project.crud.account.domain.Account;
import com.project.crud.board.domain.Board;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLikeId implements Serializable {
    private Board board;
    private Account account;

    @Builder
    public BoardLikeId(final Board board, final Account account) {
        this.board = board;
        this.account = account;
    }
}
