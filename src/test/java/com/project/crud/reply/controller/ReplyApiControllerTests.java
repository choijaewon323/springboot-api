package com.project.crud.reply.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crud.board.domain.Board;
import com.project.crud.board.domain.BoardRepository;
import com.project.crud.reply.domain.Reply;
import com.project.crud.reply.domain.ReplyRepository;
import com.project.crud.reply.dto.ReplyRequestDto;
import com.project.crud.reply.dto.ReplyResponseDto;
import com.project.crud.reply.controller.ReplyApiController;
import com.project.crud.reply.service.ReplyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReplyApiControllerTests {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    ReplyService replyService;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ReplyApiController(replyService))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @AfterEach
    void afterEach() {
        replyRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @DisplayName("댓글 생성 테스트")
    @Test
    void createTest() throws Exception {
        Board board = boardRepository.save(new Board("제목","내용","작성자"));
        ReplyRequestDto replyRequestDto = new ReplyRequestDto("내용1", "작성자1");
        String content = objectMapper.writeValueAsString(replyRequestDto);

        mockMvc.perform(
                post("/api/v1/reply/{boardId}", board.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk());

        assertThat(replyRepository.findAll().size()).isEqualTo(1);
        assertThat(replyRepository.findAll().get(0).getBoard().getId()).isEqualTo(board.getId());
    }

    @DisplayName("댓글 하나 읽어오기 테스트")
    @Test
    void readOneTest() throws Exception {
        Board board = boardRepository.save(new Board("제목","내용","작성자"));
        Reply reply = replyRepository.save(new Reply("내용1", "작성자1", board));
        ReplyResponseDto response = new ReplyResponseDto(reply.getId(), reply.getContent(), reply.getWriter());
        String expect = objectMapper.writeValueAsString(response);

        mockMvc.perform(
                get("/api/v1/reply/{replyId}", reply.getId())
        ).andExpect(status().isOk())
                .andExpect(content().string(expect));
    }

    @DisplayName("댓글 여러개 읽어오기 테스트")
    @Test
    void readAllTest() throws Exception {
        Board board = boardRepository.save(new Board("제목","내용","작성자"));
        Reply reply1 = replyRepository.save(new Reply("내용1", "작성자1", board));
        Reply reply2 = replyRepository.save(new Reply("내용1", "작성자1", board));

        List<ReplyResponseDto> results = new ArrayList<>();
        results.add(new ReplyResponseDto(reply1.getId(), reply1.getContent(), reply1.getWriter()));
        results.add(new ReplyResponseDto(reply2.getId(), reply2.getContent(), reply2.getWriter()));
        String expect = objectMapper.writeValueAsString(results);

        mockMvc.perform(
                get("/api/v1/reply/all/{boardId}", board.getId())
        ).andExpect(status().isOk())
                .andExpect(content().string(expect));
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    void updateTest() throws Exception {
        Board board = boardRepository.save(new Board("제목","내용","작성자"));
        Reply reply = replyRepository.save(new Reply("내용1", "작성자1", board));
        ReplyRequestDto requestDto = new ReplyRequestDto("내용2", "작성자2");
        String content = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(
                put("/api/v1/reply/{replyId}", reply.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isOk());

        assertThat(replyRepository.findAll().size()).isEqualTo(1);
        assertThat(replyRepository.findAll().get(0).getContent()).isEqualTo("내용2");
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    void deleteTest() throws Exception {
        Board board = boardRepository.save(new Board("제목","내용","작성자"));
        Reply reply = replyRepository.save(new Reply("내용1", "작성자1", board));

        mockMvc.perform(
                delete("/api/v1/reply/{replyId}", reply.getId())
        ).andExpect(status().isOk());

        assertThat(replyRepository.findAll().size()).isEqualTo(0);
    }
}
