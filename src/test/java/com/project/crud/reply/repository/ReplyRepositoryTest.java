package com.project.crud.reply.repository;

import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.config.QueryDSLConfig;
import com.project.crud.reply.domain.Reply;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(QueryDSLConfig.class)
@DataJpaTest
@ActiveProfiles("test")
public class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    BoardRepository boardRepository;

    Board board;

    @BeforeEach
    void init() {
        board = Board.builder()
                .title("제목")
                .content("내용")
                .writer("작성자")
                .build();
        boardRepository.save(board);
    }

    @DisplayName("board와 연관된 reply 찾기")
    @Test
    void findAllByBoard() {
        // given
        replyRepository.save(Reply.builder()
                .content("댓글 내용1")
                .writer("작성자")
                .board(board)
                .build());
        replyRepository.save(Reply.builder()
                .content("댓글 내용2")
                .writer("작성자")
                .board(board)
                .build());

        // when
        List<Reply> results = replyRepository.findAllByBoard(board.getId());

        // then
        assertThat(results.size()).isEqualTo(2);
        assertThat(results.stream().anyMatch((e) -> {
            if (e.getContent().equals("댓글 내용2")) {
                return true;
            }
            return false;
        })).isTrue();
        assertThat(results.stream().allMatch((e) -> {
            if (e.getWriter().equals("작성자")) {
                return true;
            }
            return false;
        })).isTrue();
    }
}
