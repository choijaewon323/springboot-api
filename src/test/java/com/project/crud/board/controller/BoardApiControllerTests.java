package com.project.crud.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crud.board.controller.BoardApiController;
import com.project.crud.board.domain.Board;
import com.project.crud.board.domain.BoardRepository;
import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.board.service.BoardService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
public class BoardApiControllerTests {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardService boardService;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new BoardApiController(boardService))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @AfterEach
    void deleteAll() throws Exception {
        boardRepository.deleteAll();
    }

    @DisplayName("게시물 하나 게시하기 테스트")
    @Test
    void createTest() throws Exception {
        BoardRequestDto dto = new BoardRequestDto("제목1", "내용1", "작성자1");

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk());

        assertThat(boardRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("게시물 하나 받아오기 테스트")
    @Test
    void readOneTest() throws Exception {
        Board board = boardRepository.save(new Board("제목1", "내용1", "작성자1"));
        String expected = objectMapper.writeValueAsString(board);

        mockMvc.perform(get("/api/v1/board/{boardId}", board.getId())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(content().string(expected));
    }

    @DisplayName("게시물 전체 받아오기 테스트")
    @Test
    void readAllTest() throws Exception {
        Board board1 = boardRepository.save(new Board("제목1", "내용1", "작성자1"));
        Board board2 = boardRepository.save(new Board("제목2", "내용2", "작성자2"));

        List<BoardResponseDto> expectList = new ArrayList<>();
        expectList.add(new BoardResponseDto(board1.getId(), board1.getTitle(), board1.getContent(), board1.getWriter(), board1.getLikeCount()));
        expectList.add(new BoardResponseDto(board2.getId(), board2.getTitle(), board2.getContent(), board2.getWriter(), board2.getLikeCount()));
        String expect = objectMapper.writeValueAsString(expectList);

        mockMvc.perform(
                get("/api/v1/board")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(content().string(expect));
    }

    @DisplayName("게시물 수정 테스트")
    @Test
    void updateTest() throws Exception {
        Board board = boardRepository.save(new Board("제목1", "내용1", "작성자1"));

        BoardRequestDto dto = new BoardRequestDto("제목2", "내용2", "작성자2");

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/api/v1/board/{boardId}", board.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk());

        assertThat(boardRepository.findAll().get(0).getTitle()).isEqualTo("제목2");
    }

    @DisplayName("게시물 삭제 테스트")
    @Test
    void deleteTest() throws Exception {
        Board board = boardRepository.save(new Board("제목1", "내용1", "작성자1"));

        mockMvc.perform(delete("/api/v1/board/{boardId}", board.getId())
        ).andExpect(status().isOk());

        assertThat(boardRepository.findAll().size()).isEqualTo(0);
    }
}
