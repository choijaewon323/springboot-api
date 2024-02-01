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

    final Long boardId = 0L;

    @DisplayName("board와 연관된 reply 찾기")
    @Test
    void findAllByBoard() {
        // given
        makeRepliesWithSameWriter(2);

        // when
        final List<Reply> results = replyRepository.findByBoardId(boardId);

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

    private void makeRepliesWithSameWriter(final int count) {
        for (int i = 1; i <= count; i++) {
            replyRepository.save(makeReplyWithSameWriter(i));
        }
    }

    private Reply makeReplyWithSameWriter(final int number) {
        return Reply.builder()
                .boardId(boardId)
                .writer("작성자")
                .content("댓글 내용" + number)
                .build();
    }
}
