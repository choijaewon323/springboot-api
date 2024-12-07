package com.project.crud.like.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "BOARD_LIKE")
public class BoardLike {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "BOARD_ID", nullable = false)
    private Long boardId;

    @Column(name = "ACCOUNT_ID", nullable = false)
    private Long accountId;

    @Column(name = "is_liked")
    private boolean isLiked;

    private BoardLike(final Long boardId, final Long accountId) {
        this.boardId = boardId;
        this.accountId = accountId;
        this.isLiked = false;
    }

    public static BoardLike of(Long boardId, Long accountId) {
        return new BoardLike(boardId, accountId);
    }

    public void like() {
        if (isLiked) {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다");
        }
        isLiked = true;
    }

    public void cancel() {
        if (!isLiked) {
            throw new IllegalStateException("이미 취소된 좋아요입니다");
        }
        isLiked = false;
    }
}
