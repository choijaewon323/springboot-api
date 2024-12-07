package com.project.crud.like.dto;

public record BoardLikePresentDto(
        boolean isLiked
) {
    public static BoardLikePresentDto present() {
        return new BoardLikePresentDto(true);
    }

    public static BoardLikePresentDto absent() {
        return new BoardLikePresentDto(false);
    }
}
