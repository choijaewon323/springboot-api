package com.project.crud.board.repository;

import com.project.crud.board.domain.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class BoardRepositoryTests {
    @Autowired
    BoardRepository boardRepository;

    @DisplayName("작성자 기준 board 조회 - 정확히 일치하는 writer만 색출")
    @Test
    void findByWriter() {
        // given
        givenTestBoards(2);

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
        givenTestBoards(2);
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
        givenTestBoards(2);

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
        givenTestBoards(2);

        // when
        List<Board> boards = boardRepository.searchByContent("내용");

        // then
        assertThat(boards.size()).isEqualTo(2);
    }

    @DisplayName("제목 기준으로 keyword 검색")
    @Test
    void findByTitleContainingTest() {
        // given
        givenTestBoards(2);

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
        givenTestBoards(2);

        // when
        List<Board> boards = boardRepository.findByWriterContaining("작성자1");

        // then
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.get(0).getContent()).isEqualTo("내용1");
    }

    @DisplayName("페이징 쿼리 검사 - 작성날짜 기준 내림차순")
    @Test
    void findAllPagingCreatedDateDesc() {
        // given
        givenTestBoards(10);
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdDate"));

        // when
        Page<Board> boards = boardRepository.findAll(pageRequest);
        /*
        Hibernate: select b1_0.board_id,b1_0.cnt,b1_0.content,b1_0.created_date,b1_0.like_count,b1_0.modified_date,b1_0.title,b1_0.writer from board b1_0 order by b1_0.created_date desc offset ? rows fetch first ? rows only
        Hibernate: select count(b1_0.board_id) from board b1_0

        -> select 두번 출력, count() 두번째 포함
        */

        // then
        assertThat(boards.getContent().size()).isEqualTo(5);
    }

    @DisplayName("페이징 쿼리 - id 기준 내림차순")
    @Test
    void findAllPagingIdDescTest() {
        // given
        givenTestBoards(10);
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));

        // when
        Page<Board> boards = boardRepository.findAll(pageRequest);

        // then
        assertThat(boards.getContent().size()).isEqualTo(5);
        assertThat(boards.get().anyMatch(e -> {
            if (e.getId().equals(8L)) {
                return true;
            }
            return false;
        })).isTrue();
    }

    private void givenTestBoards(final int count) {
        for (int i = 1; i <= count; i++) {
            boardRepository.save(makeBoard(i));
        }
    }

    private Board makeBoard(int number) {
        return Board.builder()
                .title("제목" + number)
                .content("내용" + number)
                .writer("작성자" + number)
                .build();
    }
}
