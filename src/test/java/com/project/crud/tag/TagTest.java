package com.project.crud.tag;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TagTest {

    @DisplayName("tag 생성 오류 : name이 null일 경우 IllegalArgumentException")
    @Test
    void newTagThrowsIllegalArgumentWhenNameNull() {
        Long boardId = 0L;

        ThrowableAssert.ThrowingCallable when = () -> Tag.of(null, boardId);

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("tag 생성 오류 : name이 blank일 경우 IllegalArgumentException")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\n", "\t\n"})
    void newTagThrowsIllegalArgumentWhenNameBlank(String name) {
        Long boardId = 0L;

        ThrowableAssert.ThrowingCallable when = () -> Tag.of(name, boardId);

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("tag 생성 오류 : boardId가 null일 경우 IllegalArgumentException")
    @Test
    void newTagThrowsIllegalWhenBoardIdIsNull() {
        ThrowableAssert.ThrowingCallable when = () -> Tag.of("name", null);

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("tag 생성 오류 : boardId가 음수일 경우 IllegalArgumentException")
    @Test
    void newTagThrowsIllegalWhenBoardIdIsNegative() {
        ThrowableAssert.ThrowingCallable when = () -> Tag.of("name", -1L);

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class);
    }
}
