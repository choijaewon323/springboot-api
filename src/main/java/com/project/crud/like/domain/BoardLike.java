package com.project.crud.like.domain;

import com.project.crud.account.domain.Account;
import com.project.crud.board.domain.Board;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "BOARD_LIKE")
@IdClass(BoardLikeId.class)
public class BoardLike {
    @Id
    @JoinColumn(name = "BOARD_ID")
    @ManyToOne
    private Board board;

    @Id
    @JoinColumn(name = "ACCOUNT_ID")
    @ManyToOne
    private Account account;
}
