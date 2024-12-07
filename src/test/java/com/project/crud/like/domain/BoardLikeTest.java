package com.project.crud.like.domain;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardLikeTest {

    @DisplayName("초기화 테스트 : 좋아요 객체 생성 시 디폴트값으로 안 눌려있어야함")
    @Test
    void initialBoardLikeNotPushed() {
        BoardLike boardLike = BoardLike.of(0L, 0L);

        assertThat(boardLike.isLiked()).isFalse();
    }

    @DisplayName("좋아요 취소 오류 : 좋아요가 이미 안 눌려있는데 취소 시 illegalStateException")
    @Test
    void throwIllegalStateIfNotLikedAndCancel() {
        BoardLike boardLike = BoardLike.of(0L, 0L);

        ThrowingCallable when = boardLike::cancel;

        assertThatThrownBy(when)
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("좋아요 누르기 오류 : 좋아요가 이미 눌려있는데 또 좋아요 시 illegalStateException")
    @Test
    void throwIllegalStateIfAlreadyLikedAndPush() {
        BoardLike boardLike = BoardLike.of(0L, 0L);
        boardLike.like();

        ThrowingCallable when = boardLike::like;

        assertThatThrownBy(when)
                .isInstanceOf(IllegalStateException.class);
    }
}
