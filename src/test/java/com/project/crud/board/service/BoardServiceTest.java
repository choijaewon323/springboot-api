package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.domain.BoardRepository;
import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class BoardServiceTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;

    @AfterEach
    void afterEach() throws Exception {
        boardRepository.deleteAll();
    }

    @DisplayName("게시판 등록 테스트")
    @Test
    void create() throws Exception {
        // test
        boardService.create(new BoardRequestDto("제목", "내용", "작성자"));

        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.get(0).getWriter()).isEqualTo("작성자");
        assertThat(boards.get(0).getLikeCount()).isEqualTo(0L);
    }

    @DisplayName("게시판 수정 테스트")
    @Test
    void update() throws Exception {

        // pre
        Board board = boardRepository.save(new Board("제목", "내용", "작성자"));

        // exec
        boardService.update(board.getId(), new BoardRequestDto("제목1", "내용1", "작성자1"));

        // test
        Board after = boardRepository.findById(board.getId())
                .orElseThrow();
        assertThat(after.getContent()).isEqualTo("내용1");
        assertThat(after.getLikeCount()).isEqualTo(0L);
    }

    @DisplayName("게시판 삭제 테스트")
    @Test
    void delete() throws Exception {
        // pre
        Board board = boardRepository.save(new Board("제목", "내용", "작성자"));

        // exec
        boardService.delete(board.getId());

        // test
        assertThat(boardRepository.findAll().isEmpty()).isTrue();
    }

    @DisplayName("게시판 전체 읽어오기 테스트")
    @Test
    void readAll() throws Exception {
        // pre
        List<Board> boards = Arrays.asList(new Board("제목", "내용", "작성자"), new Board("제목1", "내용1", "작성자1"));
        boards.stream().forEach((e) -> {
            boardRepository.save(e);
        });

        // exec
        List<BoardResponseDto> results = boardService.readAll();

        assertThat(results.size()).isEqualTo(2);
        assertThat(results.stream().anyMatch((e) -> {
            if (e.getTitle().equals("제목")) {
                return true;
            }
            return false;
        })).isTrue();
    }

    @DisplayName("게시판 하나 읽어오기 테스트")
    @Test
    void readOne() throws Exception {
        // pre
        Board board = boardRepository.save(new Board("제목", "내용", "작성자"));

        // exec
        BoardResponseDto result = boardService.readOne(board.getId());

        // test
        assertThat(result.getContent()).isEqualTo(board.getContent());
        assertThat(result.getWriter()).isEqualTo(board.getWriter());
    }
}
