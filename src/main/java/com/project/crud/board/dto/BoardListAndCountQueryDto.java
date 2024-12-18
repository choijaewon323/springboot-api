package com.project.crud.board.dto;

import com.project.crud.board.domain.Board;

import java.util.List;

public record BoardListAndCountQueryDto(
        long count,
        List<Board> boards
) {
}
