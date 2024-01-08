package com.project.crud.like.service;

import com.project.crud.account.domain.Account;
import com.project.crud.account.domain.AccountRepository;
import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.like.domain.AlreadyExistsException;
import com.project.crud.like.domain.BoardLike;
import com.project.crud.like.domain.BoardLikeId;
import com.project.crud.like.domain.NotExistsException;
import com.project.crud.like.repository.BoardLikeRepository;
import com.project.crud.like.dto.BoardLikeRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
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
    @Transactional
    public void up(BoardLikeRequestDto request) {
        Board board = boardRepository.pessimisticFindById(request.getBoardId())
                .orElseThrow(() -> new NoSuchElementException("no such board"));

        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementException("no such username"));

        checkExists(board, account);

        boardLikeRepository.save(new BoardLike(board, account));
        board.likeUp();
    }

    @Override
    @Transactional
    public void down(BoardLikeRequestDto request) {
        Board board = boardRepository.pessimisticFindById(request.getBoardId())
                .orElseThrow(() -> new NoSuchElementException("no such board"));

        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementException("no such username"));

        checkNotExists(board, account);

        boardLikeRepository.deleteById(new BoardLikeId(board, account));
        board.likeDown();
    }

    private void checkExists(final Board board, final Account account) {
        BoardLikeId id = new BoardLikeId(board, account);

        Boolean test = boardLikeRepository.findById(id).isPresent();

        if (test) {
            throw new AlreadyExistsException("이미 존재하는 좋아요입니다.");
        }
    }

    private void checkNotExists(final Board board, final Account account) {
        BoardLikeId id = new BoardLikeId(board, account);

        Boolean test = boardLikeRepository.findById(id).isPresent();

        if (!test) {
            throw new NotExistsException("존재하지 않는 좋아요입니다.");
        }
    }
}
