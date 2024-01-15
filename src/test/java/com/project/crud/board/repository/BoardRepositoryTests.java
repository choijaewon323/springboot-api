package com.project.crud.board.repository;

import com.project.crud.board.domain.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class BoardRepositoryTests {
    @Autowired
    BoardRepository boardRepository;

    @DisplayName("작성자 기준 board 조회")
    @Test
    void findByWriter() {
        // given
        givenTestBoards();

        // when
        List<Board> boards = boardRepository.findByWriter("작성자2");

        // then
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.get(0).getContent()).isEqualTo("내용2");
    }

    @DisplayName("작성자 기준 board 삭제 테스트")
    @Test
    void deleteByWriter() {
        // given
        givenTestBoards();
        boardRepository.save(Board.builder()
                        .title("제목")
                        .content("내용")
                        .writer("작성자2")
                .build());


        // when
        boardRepository.deleteByWriter("작성자2");

        // then
        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isEqualTo(1L);
        assertThat(boards.get(0).getWriter()).isEqualTo("작성자1");
    }

    @DisplayName("내용 기준으로 keyword 검색하기 - 정확히 일치할 경우")
    @Test
    void searchByContentTest() {
        // given
        givenTestBoards();

        // when
        List<Board> boards = boardRepository.searchByContent("내용2");

        // then
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.get(0).getContent()).isEqualTo("내용2");
    }

    @DisplayName("내용 기준으로 keyword 검색하기 - keyword가 포함된 경우")
    @Test
    void searchByContentContainsTest() {
        // given
        givenTestBoards();

        // when
        List<Board> boards = boardRepository.searchByContent("내용");

        // then
        assertThat(boards.size()).isEqualTo(2);
    }

    @DisplayName("제목 기준으로 keyword 검색")
    @Test
    void findByTitleContainingTest() {
        // given
        givenTestBoards();

        // when
        List<Board> boards = boardRepository.findByTitleContaining("제목1");

        // then
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.get(0).getContent()).isEqualTo("내용1");
    }

    @DisplayName("작성자 기준으로 keyword 검색")
    @Test
    void findByWriterContainingTest() {
        // given
        givenTestBoards();

        // when
        List<Board> boards = boardRepository.findByWriterContaining("작성자1");

        // then
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.get(0).getContent()).isEqualTo("내용1");
    }

    private void givenTestBoards() {
        boardRepository.save(makeBoard(1));
        boardRepository.save(makeBoard(2));
    }

    private Board makeBoard(int number) {
        return Board.builder()
                .title("제목" + number)
                .content("내용" + number)
                .writer("작성자" + number)
                .build();
    }
}
