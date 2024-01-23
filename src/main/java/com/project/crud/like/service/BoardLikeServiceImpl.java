package com.project.crud.like.service;

import com.project.crud.account.domain.Account;
import com.project.crud.account.repository.AccountRepository;
import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.like.domain.BoardLike;
import com.project.crud.like.domain.BoardLikeId;
import com.project.crud.like.repository.BoardLikeRepository;
import com.project.crud.like.dto.BoardLikeRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class BoardLikeServiceImpl implements BoardLikeService {
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;
    private final BoardLikeRepository boardLikeRepository;

    public BoardLikeServiceImpl(BoardRepository boardRepository,
                                AccountRepository accountRepository,
                                BoardLikeRepository boardLikeRepository) {
        this.boardRepository = boardRepository;
        this.accountRepository = accountRepository;
        this.boardLikeRepository = boardLikeRepository;
    }

    @Override
    public void up(BoardLikeRequestDto request) {
        Board board = findExistBoardPessimistic(request.getBoardId());
        Account account = findExistAccountByUsername(request.getUsername());

        checkExists(board, account);

        boardLikeRepository.save(request.toEntity(account, board));
        board.likeUp();
    }

    @Override
    public void down(BoardLikeRequestDto request) {
        Board board = findExistBoardPessimistic(request.getBoardId());
        Account account = findExistAccountByUsername(request.getUsername());

        checkNotExists(board, account);

        boardLikeRepository.deleteById(BoardLikeId.builder()
                .board(board)
                .account(account)
                .build());
        board.likeDown();
    }

    private void checkExists(final Board board, final Account account) {
        BoardLikeId id = BoardLikeId.builder()
                .board(board)
                .account(account)
                .build();

        Boolean test = boardLikeRepository.findById(id).isPresent();

        if (test) {
            throw new IllegalStateException("이미 존재하는 좋아요입니다");
        }
    }

    private void checkNotExists(final Board board, final Account account) {
        BoardLikeId id = BoardLikeId.builder()
                .board(board)
                .account(account)
                .build();

        Boolean test = boardLikeRepository.findById(id).isPresent();

        if (!test) {
            throw new IllegalStateException("존재하지 않는 좋아요입니다");
        }
    }

    private Board findExistBoardPessimistic(final Long boardId) {
        Board board = boardRepository.pessimisticFindById(boardId)
                .orElseThrow(() -> new NoSuchElementException("해당 board를 찾을 수 없습니다"));

        return board;
    }

    private Account findExistAccountByUsername(final String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당 username을 찾을 수 없습니다"));

        return account;
    }
}
