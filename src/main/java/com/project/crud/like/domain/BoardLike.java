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
    @Column(name = "BOARD_ID", nullable = false)
    private Long boardId;

    @Id
    @Column(name = "ACCOUNT_ID", nullable = false)
    private Long accountId;

    @Builder
    public BoardLike(final Long boardId, final Long accountId) {
        this.boardId = boardId;
        this.accountId = accountId;
    }
}
