package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BoardServiceTest {
    @InjectMocks
    BoardServiceImpl boardService;

    @Mock
    BoardRepository boardRepository;

    Board board;

    @BeforeEach
    void init() {
        board = Board.builder()
                .title("제목")
                .content("내용")
                .writer("작성자")
                .build();
    }

    @DisplayName("게시판 등록 테스트")
    @Test
    void create() throws Exception {
        // given
        given(boardRepository.save(any())).willReturn(board);

        // when
        boardService.create(BoardRequestDto.builder()
                .title("제목")
                .content("내용")
                .writer("작성자")
                .build());

        // then
        verify(boardRepository).save(any());
    }

    @DisplayName("게시판 수정 테스트")
    @Test
    void update() throws Exception {
        // given
        BoardRequestDto dto = BoardRequestDto.builder()
                                .title("제목2")
                                .content("내용2")
                                .writer("작성자2")
                                .build();

        given(boardRepository.findById(any())).willReturn(Optional.of(board));

        // when
        boardService.update(board.getId(), dto);

        // then
        assertThat(board.getContent()).isEqualTo("내용2");
    }

    @DisplayName("게시판 삭제 테스트")
    @Test
    void delete() throws Exception {
        // given
        doNothing().when(boardRepository).deleteById(anyLong());

        // when
        boardService.delete(0L);

        // then
        verify(boardRepository).deleteById(anyLong());
    }

    @DisplayName("게시판 전체 읽어오기 테스트")
    @Test
    void readAll() throws Exception {
        // given
        List<Board> boards = Arrays.asList(
                Board.builder()
                        .title("제목")
                        .content("내용")
                        .writer("작성자")
                        .build(),
                Board.builder()
                        .title("제목1")
                        .content("내용1")
                        .writer("작성자1")
                        .build()
                );
        given(boardRepository.findAll()).willReturn(boards);

        // when
        List<BoardResponseDto> results = boardService.readAll();

        assertThat(results.size()).isEqualTo(2);
        assertThat(results.stream().anyMatch((e) -> {
            if (e.getTitle().equals("제목")) {
                return true;
            }
            return false;
        })).isTrue();
    }

    @DisplayName("게시판 하나 읽어오기 예외 테스트")
    @Test
    void readOneException() throws Exception {
        // given
        given(boardRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> {
            BoardResponseDto result = boardService.readOne(0L);
        })
                // then
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("게시판 하나 읽어오기 테스트")
    @Test
    void readOne() {
        // given
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));

        // when
        BoardResponseDto dto = boardService.readOne(0L);

        // then
        assertThat(dto.getLikeCount()).isEqualTo(0L);
        assertThat(dto.getContent()).isEqualTo("내용");
    }
}
