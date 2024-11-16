package com.project.crud.reply.domain;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReplyTest {
    @DisplayName("reply 생성 테스트 - content 길이는 300자 이하여야함")
    @Test
    void whenCreatedContentLengthUnder300() {
        // given
        // when
        ThrowingCallable when = () -> Reply.builder()
                .content(makeContentOver300())
                .writer("writer")
                .boardId(0L)
                .build();

        // then
        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class);
    }

    private String makeContentOver300() {
        StringBuilder sb = new StringBuilder();

        IntStream.iterate(1, i -> i + 1)
                .limit(310)
                .forEach(sb::append);

        return sb.toString();
    }
}
