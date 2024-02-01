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

    public BoardLikeServiceImpl(final BoardRepository boardRepository,
                                final AccountRepository accountRepository,
                                final BoardLikeRepository boardLikeRepository) {
        this.boardRepository = boardRepository;
        this.accountRepository = accountRepository;
        this.boardLikeRepository = boardLikeRepository;
    }

    @Override
    public void up(final BoardLikeRequestDto request) {
        final Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new NoSuchElementException("해당 게시물이 없습니다"));
        final Account account = accountRepository.findByUsername(request.getUsername())
                        .orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다"));

        checkExists(board, account);

        boardLikeRepository.save(request.toEntity(account.getId()));
        board.likeUp();
    }

    @Override
    public void down(final BoardLikeRequestDto request) {
        final Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new NoSuchElementException("해당 게시물이 없습니다"));
        final Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다"));

        checkNotExists(board, account);

        boardLikeRepository.deleteById(BoardLikeId.builder()
                .boardId(board.getId())
                .accountId(account.getId())
                .build());

        board.likeDown();
    }

    private void checkExists(final Board board, final Account account) {
        final BoardLikeId id = BoardLikeId.builder()
                .boardId(board.getId())
                .accountId(account.getId())
                .build();

        final boolean result = boardLikeRepository.findById(id).isPresent();

        if (result) {
            throw new IllegalStateException("이미 존재하는 좋아요입니다");
        }
    }

    private void checkNotExists(final Board board, final Account account) {
        final BoardLikeId id = BoardLikeId.builder()
                .boardId(board.getId())
                .accountId(account.getId())
                .build();

        final boolean result = boardLikeRepository.findById(id).isPresent();

        if (!result) {
            throw new IllegalStateException("존재하지 않는 좋아요입니다");
        }
    }
}
