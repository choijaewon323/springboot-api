package com.project.crud.like.service;

import com.project.crud.account.domain.Account;
import com.project.crud.account.repository.AccountRepository;
import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.exception.CustomException;
import com.project.crud.exception.ErrorCode;
import com.project.crud.like.domain.BoardLike;
import com.project.crud.like.dto.BoardLikePresentDto;
import com.project.crud.like.dto.BoardLikeRequestDto;
import com.project.crud.like.repository.BoardLikeQueryRepository;
import com.project.crud.like.repository.BoardLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.project.crud.exception.ErrorCode.ACCOUNT_NOT_FOUND;
import static com.project.crud.exception.ErrorCode.BOARD_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardLikeService {
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardLikeQueryRepository boardLikeQueryRepository;

    @Transactional(readOnly = true)
    public BoardLikePresentDto isLikePushed(BoardLikeRequestDto request) {
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ACCOUNT_NOT_FOUND, "해당 유저가 없습니다"));

        Optional<BoardLike> nullableBoardLike = boardLikeQueryRepository.findBoardLikeIfPresent(request.getBoardId(), account.getId());

        if (nullableBoardLike.isEmpty()) {
            return BoardLikePresentDto.absent();
        }

        BoardLike boardLike = nullableBoardLike.get();
        if (boardLike.isLiked()) {
            return BoardLikePresentDto.present();
        }

        return BoardLikePresentDto.absent();
    }

    public void up(BoardLikeRequestDto request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND, "해당 게시글이 없습니다"));
        Account account = accountRepository.findByUsername(request.getUsername())
                        .orElseThrow(() -> new CustomException(ACCOUNT_NOT_FOUND, "해당 유저가 없습니다"));

        Optional<BoardLike> nullableBoardLike = boardLikeQueryRepository.findBoardLikeIfPresent(request.getBoardId(), account.getId());

        if (nullableBoardLike.isEmpty()) {
            BoardLike newLike = BoardLike.of(request.getBoardId(), account.getId());
            newLike.like();
            board.likeUp();

            boardLikeRepository.save(newLike);

            return;
        }

        BoardLike boardLike = nullableBoardLike.get();

        boardLike.like();
        board.likeUp();
    }

    public void down(BoardLikeRequestDto request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND, "해당 게시물이 없습니다"));
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ACCOUNT_NOT_FOUND, "해당 유저가 없습니다"));

        Optional<BoardLike> nullableBoardLike = boardLikeQueryRepository.findBoardLikeIfPresent(request.getBoardId(), account.getId());

        if (nullableBoardLike.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_LIKE_OPERATION, "좋아요를 취소할 수 없습니다");
        }

        BoardLike boardLike = nullableBoardLike.get();

        boardLike.cancel();
        board.likeDown();
    }
}
