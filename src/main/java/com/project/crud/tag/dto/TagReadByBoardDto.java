package com.project.crud.tag.dto;

import com.project.crud.tag.Tag;

public record TagReadByBoardDto(
        Long id,
        String name,
        Long boardId
) {
    public static TagReadByBoardDto of(Tag tag) {
        return new TagReadByBoardDto(tag.getId(),
                tag.getName(),
                tag.getBoardId());
    }
}
