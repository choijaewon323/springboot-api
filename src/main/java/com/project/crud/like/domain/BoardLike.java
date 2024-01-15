package com.project.crud.like.domain;

import com.project.crud.account.domain.Account;
import com.project.crud.board.domain.Board;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "BOARD_LIKE")
@IdClass(BoardLikeId.class)
public class BoardLike {
    @Id
    @JoinColumn(name = "BOARD_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Id
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Builder
    public BoardLike(Board board, Account account) {
        this.board = board;
        this.account = account;
    }
}
