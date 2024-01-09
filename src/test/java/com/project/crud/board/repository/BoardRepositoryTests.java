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
        boardRepository.save(makeBoard(1));
        boardRepository.save(makeBoard(2));

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
        boardRepository.save(makeBoard(1));
        boardRepository.save(makeBoard(2));

        // when
        boardRepository.deleteByWriter("작성자2");

        // then
        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isEqualTo(1L);
        assertThat(boards.get(0).getWriter()).isEqualTo("작성자1");
    }

    private Board makeBoard(int number) {
        return new Board("제목" + number, "내용" + number, "작성자" + number);
    }
}
