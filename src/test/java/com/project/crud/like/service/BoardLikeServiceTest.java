package com.project.crud.like.service;

import com.project.crud.account.domain.Account;
import com.project.crud.account.domain.AccountRepository;
import com.project.crud.board.domain.Board;
import com.project.crud.board.domain.BoardRepository;
import com.project.crud.like.domain.BoardLike;
import com.project.crud.like.domain.BoardLikeRepository;
import com.project.crud.like.dto.BoardLikeRequest;
import com.project.crud.security.enums.AccountRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
public class BoardLikeServiceTest {
    @Autowired
    BoardLikeService boardLikeService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BoardLikeRepository boardLikeRepository;

    final Long NUMBER_OF_THREADS = 100L;

    @AfterEach
    void afterEach() throws Exception {
        boardLikeRepository.deleteAll();
        boardRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @DisplayName("좋아요 정상 동작 테스트")
    @Test
    void upTest() throws Exception {
        Board board = boardRepository.save(new Board("제목", "내용", "글쓴이"));
        Account account = accountRepository.save(new Account("유저네임", "패스워드", AccountRole.USER));

        // test
        boardLikeService.up(new BoardLikeRequest(board.getId(), account.getUsername()));

        // then
        List<BoardLike> boardLikes = boardLikeRepository.findAll();
        assertThat(boardLikes.size()).isEqualTo(1);
        assertThat(boardLikes.get(0).getBoard().getId()).isEqualTo(board.getId());
        assertThat(boardLikes.get(0).getAccount().getUsername()).isEqualTo(account.getUsername());
    }

    @DisplayName("좋아요 증가 오류 테스트 - 이미 있는 좋아요")
    @Test
    @Transactional
    void upTestException() throws Exception {
        Board board = boardRepository.save(new Board("제목", "내용", "글쓴이"));
        Account account = accountRepository.save(new Account("유저네임", "패스워드", AccountRole.USER));

        boardLikeRepository.save(new BoardLike(board, account));

        // test
        assertThatThrownBy(() -> {
            boardLikeService.up(new BoardLikeRequest(board.getId(), account.getUsername()));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("좋아요에 대한 board의 좋아요 수 유효성 검사")
    @Test
    void boardLikeCount() throws Exception {
        Board board = boardRepository.save(new Board("제목", "내용", "글쓴이"));
        Account account = accountRepository.save(new Account("유저네임", "패스워드", AccountRole.USER));

        // test
        boardLikeService.up(new BoardLikeRequest(board.getId(), account.getUsername()));

        Board afterBoard = boardRepository.findById(board.getId())
                .orElseThrow(NoSuchElementException::new);

        assertThat(afterBoard.getLikeCount()).isEqualTo(1L);
    }

    @DisplayName("좋아요 삭제 정상 동작 확인")
    @Test
    @Transactional
    void downTest() throws Exception {
        Board board = boardRepository.save(new Board("제목", "내용", "글쓴이"));
        Account account = accountRepository.save(new Account("유저네임", "패스워드", AccountRole.USER));

        boardLikeRepository.save(new BoardLike(board, account));

        // test
        boardLikeService.down(new BoardLikeRequest(board.getId(), account.getUsername()));

        List<BoardLike> boardLikes = boardLikeRepository.findAll();
        assertThat(boardLikes.isEmpty()).isTrue();
    }

    @DisplayName("좋아요 삭제 예외 상황 확인 - 게시글과 유저의 조합이 없는 경우")
    @Test
    void downTestException() throws Exception {
        Board board = boardRepository.save(new Board("제목", "내용", "글쓴이"));
        Account account = accountRepository.save(new Account("유저네임", "패스워드", AccountRole.USER));

        // test
        assertThatThrownBy(() -> {
            boardLikeService.down(new BoardLikeRequest(board.getId(), account.getUsername()));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("좋아요 멀티스레드 테스트")
    @Test
    void multiThread() throws Exception {
        Board board = boardRepository.save(new Board("제목", "내용", "글쓴이"));
        Account account = accountRepository.save(new Account("유저네임", "패스워드", AccountRole.USER));

        ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS.intValue());
        CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS.intValue());

        // test
        for (Integer i = 0; i < NUMBER_OF_THREADS; i++) {
            service.execute(() -> {
                try {
                    boardLikeService.up(new BoardLikeRequest(board.getId(), account.getUsername()));
                    latch.countDown();
                } catch (Exception e) {
                    System.out.println("##############" + e.getMessage() + "#############");
                    latch.countDown();
                }
            });
        }

        latch.await();

        Board after = boardRepository.findById(board.getId())
                        .orElseThrow(NoSuchElementException::new);
        assertThat(after.getLikeCount()).isEqualTo(1L);
    }
}