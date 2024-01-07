package com.project.crud.reply.domain;

import com.project.crud.board.domain.Board;
import com.project.crud.reply.dto.ReplyRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReplyTest {

    Board board;

    @BeforeEach
    void init() {
        board = new Board("제목", "게시글 내용", "게시글 작성자");
    }

    @DisplayName("Reply update 테스트")
    @Test
    void update() {
        // given
        Reply reply = new Reply("내용", "작성자", board);

        ReplyRequestDto dto = ReplyRequestDto.builder()
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
