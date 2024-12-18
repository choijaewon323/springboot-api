package com.project.crud.board.dto;

import java.util.List;

public record BoardCreateWithTagDto(
        String title,
        String content,
        String writer,
        List<String> tags
) {
}
