package com.project.crud.like.service;

import com.project.crud.account.domain.Account;
import com.project.crud.account.repository.AccountRepository;
import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.like.domain.BoardLike;
import com.project.crud.like.domain.BoardLikeId;
import com.project.crud.like.repository.BoardLikeRepository;
import com.project.crud.like.dto.BoardLikeRequestDto;
import com.project.crud.account.domain.AccountRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BoardLikeServiceTest {
    @InjectMocks
    BoardLikeServiceImpl boardLikeService;

    @Mock
    BoardLikeRepository boardLikeRepository;
    @Mock
    BoardRepository boardRepository;
    @Mock
    AccountRepository accountRepository;

    Board board;
    Account account;

    @BeforeEach
    void init() {
        board = new Board(0L, "제목", "내용", "글쓴이", 0L, 0L);
        account = new Account("유저 이름", "비밀번호", AccountRole.USER);
    }

    @DisplayName("좋아요 정상 동작 테스트")
    @Test
    void upTest() throws Exception {
        // given
        BoardLike boardLike = new BoardLike(board, account);
        BoardLikeId id = new BoardLikeId(board, account);

        commonGiven();
        given(boardLikeRepository.findById(any())).willReturn(Optional.empty());
        given(boardLikeRepository.save(any())).willReturn(boardLike);

        // when
        boardLikeService.up(new BoardLikeRequestDto(board.getId(), account.getUsername()));

        // then
        verify(boardLikeRepository).save(any());
    }

    @DisplayName("좋아요 증가 오류 테스트 - 이미 있는 좋아요")
    @Test
    void upTestException() throws Exception {
        // given
        BoardLike boardLike = new BoardLike(board, account);
        BoardLikeId id = new BoardLikeId(board, account);

        commonGiven();
        given(boardLikeRepository.findById(any())).willReturn(Optional.of(boardLike));

        // when
        assertThatThrownBy(() -> {
            boardLikeService.up(new BoardLikeRequestDto(board.getId(), account.getUsername()));
        })
                // then
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("좋아요에 대한 board의 좋아요 수 유효성 검사")
    @Test
    void boardLikeCount() throws Exception {
        // given
        BoardLike boardLike = new BoardLike(board, account);
        BoardLikeId id = new BoardLikeId(board, account);

        commonGiven();
        given(boardLikeRepository.findById(any())).willReturn(Optional.empty());
        given(boardLikeRepository.save(any())).willReturn(boardLike);

        // when
        boardLikeService.up(new BoardLikeRequestDto(board.getId(), account.getUsername()));

        // then
        assertThat(board.getLikeCount()).isEqualTo(1L);
    }

    @DisplayName("좋아요 삭제 정상 동작 확인")
    @Test
    @Transactional
    void downTest() throws Exception {
        // given
        Board board = likeAppliedGiven();
        BoardLike boardLike = new BoardLike(board, account);
        given(boardLikeRepository.findById(any())).willReturn(Optional.of(boardLike));
        doNothing().when(boardLikeRepository).deleteById(any());

        // when
        boardLikeService.down(new BoardLikeRequestDto(board.getId(), account.getUsername()));

        // then
        verify(boardLikeRepository).deleteById(any());
    }

    @DisplayName("좋아요 삭제 예외 상황 확인 - 존재하지 않는 좋아요")
    @Test
    void downTestException() throws Exception {
        // given
        commonGiven();
        given(boardLikeRepository.findById(any())).willReturn(Optional.empty());

        // test
        assertThatThrownBy(() -> {
            boardLikeService.down(new BoardLikeRequestDto(board.getId(), account.getUsername()));
        })
                // then
                .isInstanceOf(IllegalStateException.class);
    }

    private void commonGiven() {
        given(boardRepository.pessimisticFindById(0L)).willReturn(Optional.of(board));
        given(accountRepository.findByUsername("유저 이름")).willReturn(Optional.of(account));
    }

    private Board likeAppliedGiven() {
        Board board = new Board(0L, "제목", "내용", "작성자", 1L, 0L);
        given(boardRepository.pessimisticFindById(0L)).willReturn(Optional.of(board));
        given(accountRepository.findByUsername("유저 이름")).willReturn(Optional.of(account));
        return board;
    }
}