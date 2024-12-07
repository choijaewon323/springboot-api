package com.project.crud.board.domain;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @DisplayName("board 객체의 좋아요는 처음에 무조건 0이어야 함")
    @Test
    void initialTest() {
        // given
        Board board = Board.of("title", "content", "writer");

        // when
        final long likeCount = board.getLikeCount();

        // then
        assertThat(likeCount).isZero();
    }

    @DisplayName("좋아요 취소 테스트 예외 발생 - 음수 발생 불가능")
    @Test
    void likeDownExceptionTest() {
        // given
        Board board = Board.of("title", "content", "writer");

        // when
        ThrowingCallable when = board::likeDown;

        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class);   // then
    }

    @DisplayName("board 객체 생성 테스트 - title 100글자 초과 시 IllegalStateException")
    @Test
    void ifTitleOver100ThrowsIllegalStateWhenCreated() {
        // given
        // when
        ThrowingCallable when = () -> Board.of(
                "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123",
                "content",
                "writer"
                );
        // then
        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class);
    }
}
