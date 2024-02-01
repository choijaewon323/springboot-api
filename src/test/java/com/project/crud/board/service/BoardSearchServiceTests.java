package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.board.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BoardSearchServiceTests {
    @InjectMocks
    BoardSearchServiceImpl boardSearchService;

    @Mock
    BoardRepository boardRepository;

    List<Board> givenBoards;

    @BeforeEach
    void init() {
        givenBoards = givenBoards(3);
    }

    @DisplayName("제목 기반 검색 테스트")
    @Test
    void searchByTitleTest() {
        // given
        given(boardRepository.findByTitleContaining(anyString())).willReturn(givenBoards);

        // when
        List<BoardResponseDto> results = boardSearchService.searchInTitle("제목");

        // then
        assertThat(results.size()).isEqualTo(givenBoards.size());
    }

    @DisplayName("내용 기반 검색 테스트")
    @Test
    void searchByContentTest() {
        // given
        given(boardRepository.findByContentContaining(anyString())).willReturn(givenBoards);

        // when
        List<BoardResponseDto> results = boardSearchService.searchInContent("내용");

        // then
        assertThat(results.size()).isEqualTo(givenBoards.size());
    }

    @DisplayName("작성자 기반 검색 테스트")
    @Test
    void searchByWriterTest() {
        // given
        List<Board> givenBoards = givenBoards(3);
        given(boardRepository.findByWriterContaining(anyString())).willReturn(givenBoards);

        // when
        List<BoardResponseDto> results = boardSearchService.searchInWriter("제목");

        // then
        assertThat(results.size()).isEqualTo(givenBoards.size());
    }

    private List<Board> givenBoards(final int count) {
        List<Board> results = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            results.add(makeBoard(i));
        }

        return results;
    }

    private Board makeBoard(final int number) {
        return Board.builder()
                .title("제목" + 1)
                .content("내용" + 1)
                .writer("작성자" + 1)
                .build();
    }
}
