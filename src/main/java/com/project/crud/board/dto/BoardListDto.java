package com.project.crud.board.dto;

import com.project.crud.board.domain.Board;

public record BoardListDto(
        long id,
        String title,
        long likeCount
) {

    public static BoardListDto ofBanned(Board board) {
        return new BoardListDto(board.getId(), "신고된 게시물입니다", 0);
    }

    public static BoardListDto of(Board board) {
        return new BoardListDto(board.getId(), board.getTitle(), board.getLikeCount());
    }
}
