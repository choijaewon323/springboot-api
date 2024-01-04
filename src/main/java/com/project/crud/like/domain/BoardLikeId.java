package com.project.crud.like.domain;

import com.project.crud.account.domain.Account;
import com.project.crud.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BoardLikeId implements Serializable {
    private Board board;
    private Account account;
}
