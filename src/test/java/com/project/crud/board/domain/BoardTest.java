package com.project.crud.board.domain;

import com.project.crud.board.dto.BoardRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
public class BoardTest {

    Board board;

    @BeforeEach
    void init() {
        board = new Board("제목1", "내용", "작성자");
    }

    @DisplayName("board 객체의 좋아요는 처음에 무조건 0이어야 함")
    @Test
    void initialTest() {
        assertThat(board.getLikeCount()).isEqualTo(0L);
    }

    @DisplayName("게시판 업데이트 테스트")
    @Test
    void updateTest() {
        // given
        BoardRequestDto dto = BoardRequestDto.builder()
                .title("제목111")
                .content("내용111")
                .writer("작성자111")
                .build();

        // when
        board.update(dto);

        // then
        assertThat(board.getContent()).isEqualTo("내용111");
    }

    @DisplayName("좋아요 누르기 테스트")
    @Test
    void likeUpTest() {
        // when
        board.likeUp();

        // then
        assertThat(board.getLikeCount()).isEqualTo(1L);
    }

    @DisplayName("좋아요 취소 테스트 예외 발생 - 음수 발생 불가능")
    @Test
    void likeDownExceptionTest() {
        assertThatThrownBy(() -> {
            // when
            board.likeDown();
        }).isInstanceOf(IllegalStateException.class);   // then
    }
}
