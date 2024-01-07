package com.project.crud.like.service;

import com.project.crud.account.domain.Account;
import com.project.crud.account.domain.AccountRepository;
import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.like.domain.BoardLike;
import com.project.crud.like.domain.BoardLikeId;
import com.project.crud.like.domain.BoardLikeRepository;
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

        Boolean present = boardLikeRepository.findById(new BoardLikeId(board, account)).isPresent();

        if (present) {
            throw new IllegalArgumentException("already exist");
        }

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

        BoardLikeId id = new BoardLikeId(board, account);
        Boolean present = boardLikeRepository.findById(id).isPresent();

        if (!present) {
            throw new IllegalArgumentException("not exist");
        }

        boardLikeRepository.deleteById(id);
        board.likeDown();
    }
}
