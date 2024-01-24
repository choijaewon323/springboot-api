package com.project.crud.reply.domain;

import com.project.crud.board.domain.Board;
import com.project.crud.reply.dto.ReplyRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReplyTest {

    Board board;
    Reply reply;

    @BeforeEach
    void init() {
        board = Board.builder()
                .title("제목")
                .content("게시글 내용")
                .writer("게시글 작성자")
                .build();
        reply = Reply.builder()
                .writer("작성자")
                .content("내용")
                .board(board)
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
