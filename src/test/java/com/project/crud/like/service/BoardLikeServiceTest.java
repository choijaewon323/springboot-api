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
        board = Board.builder()
                .title("제목")
                .content("내용")
                .writer("글쓴이")
                .build();
        account = Account.builder()
                .username("유저 이름")
                .password("비밀번호")
                .role(AccountRole.USER)
                .build();
    }

    @DisplayName("좋아요 정상 동작 테스트")
    @Test
    void upTest() throws Exception {
        // given
        BoardLike boardLike = BoardLike.builder()
                .board(board)
                .account(account)
                .build();

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
        BoardLike boardLike = BoardLike.builder()
                .board(board)
                .account(account)
                .build();

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
        BoardLike boardLike = BoardLike.builder()
                .board(board)
                .account(account)
                .build();

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
        BoardLike boardLike = BoardLike.builder()
                .board(board)
                .account(account)
                .build();
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
        given(boardRepository.pessimisticFindById(any())).willReturn(Optional.of(board));
        given(accountRepository.findByUsername("유저 이름")).willReturn(Optional.of(account));
    }

    private Board likeAppliedGiven() {
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .writer("작성자")
                .build();
        board.likeUp();
        given(boardRepository.pessimisticFindById(any())).willReturn(Optional.of(board));
        given(accountRepository.findByUsername("유저 이름")).willReturn(Optional.of(account));
        return board;
    }
}