package com.project.crud.reply.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.reply.domain.Reply;
import com.project.crud.reply.dto.ReplyRequestDto;
import com.project.crud.reply.dto.ReplyResponseDto;
import com.project.crud.reply.repository.ReplyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ReplyServiceTests {

    @InjectMocks
    ReplyServiceImpl replyService;

    @Mock
    ReplyRepository replyRepository;
    @Mock
    BoardRepository boardRepository;

    static Board board;

    @BeforeAll
    static void init() {
        board = new Board("제목", "내용", "작성자");
    }

    @DisplayName("댓글 생성 작동 테스트")
    @Test
    void create() {
        // given
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));
        given(replyRepository.save(any())).willReturn(new Reply("댓글 내용", "작성자", board));
        Long boardId = 0L;
        ReplyRequestDto dto = ReplyRequestDto.builder()
                .content("댓글 내용")
                .writer("작성자")
                .build();

        // when
        replyService.create(boardId, dto);

        // then
        verify(replyRepository).save(any());
        verify(boardRepository).findById(anyLong());
    }

    @DisplayName("댓글 전체 읽기 테스트")
    @Test
    void readAll() {
        // given
        given(replyRepository.findAllByBoard(0L)).willReturn(replies());

        // when
        List<ReplyResponseDto> results = replyService.readAll(0L);

        // then
        assertThat(results.size()).isEqualTo(2);
        assertThat(results.stream().anyMatch(e -> {
            if (e.getContent().equals("내용1")) {
                return true;
            }
            return false;
        })).isTrue();
        assertThat(results.stream().allMatch(e -> {
            if (e.getWriter().equals("작성자")) {
                return true;
            }
            return false;
        })).isTrue();
    }

    @DisplayName("댓글 하나 읽기 테스트")
    @Test
    void readOne() {
        // given
        Reply reply = new Reply("내용", "작성자", board);
        given(replyRepository.findById(anyLong())).willReturn(Optional.of(reply));

        // when
        ReplyResponseDto result = replyService.readOne(0L);

        // then
        assertThat(result.getContent()).isEqualTo("내용");
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    void update() {
        // given
        Reply reply = new Reply("내용", "작성자", board);
        given(replyRepository.findById(anyLong())).willReturn(Optional.of(reply));
        ReplyRequestDto dto = ReplyRequestDto.builder()
                .content("내용수정")
                .writer("작성자")
                .build();

        // when
        replyService.update(0L, dto);

        // then
        assertThat(reply.getContent()).isEqualTo("내용수정");
        assertThat(reply.getWriter()).isEqualTo("작성자");
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    void delete() {
        // given
        doNothing().when(replyRepository).deleteById(anyLong());

        // when
        replyService.delete(0L);

        // then
        verify(replyRepository).deleteById(0L);
    }

    private List<Reply> replies() {
        List<Reply> results = new ArrayList<>();
        results.add(new Reply("내용1", "작성자", board));
        results.add(new Reply("내용2", "작성자", board));

        return results;
    }
}
