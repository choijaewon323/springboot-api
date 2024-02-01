package com.project.crud.reply.domain;

import com.project.crud.board.domain.Board;
import com.project.crud.reply.dto.ReplyRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReplyTest {

    Reply reply;

    @BeforeEach
    void init() {
        reply = Reply.builder()
                .writer("작성자")
                .content("내용")
                .boardId(0L)
                .build();
    }

    @DisplayName("Reply update 테스트")
    @Test
    void update() {
        // given
        final ReplyRequestDto dto = ReplyRequestDto.builder()
                .content("내용2")
                .writer("작성자")
                .build();

        // when
        reply.update(dto);

        // then
        assertThat(reply.getContent()).isEqualTo("내용2");
        assertThat(reply.getWriter()).isEqualTo("작성자");
    }
}
