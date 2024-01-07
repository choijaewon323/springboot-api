package com.project.crud.reply.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.domain.BoardRepository;
import com.project.crud.reply.domain.Reply;
import com.project.crud.reply.domain.ReplyRepository;
import com.project.crud.reply.service.ReplyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ReplyServiceTests {
    @Autowired
    ReplyService replyService;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    BoardRepository boardRepository;

    @AfterEach
    void afterEach() {
        replyRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @DisplayName("Query 작동 테스트")
    @Test
    void readAllTest() {
        Board board = new Board("제목", "내용", "작성자");
        boardRepository.save(board);
        replyRepository.save(new Reply("내용1", "작성자1", board));
        replyRepository.save(new Reply("내용2", "작성자2", board));

        assertThat(replyService.readAll(board.getId()).size()).isEqualTo(2);
    }


}
