package com.project.crud.board.dto;

public record BoardListDto(
        long id,
        String title,
        long likeCount
) {
}
