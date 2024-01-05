package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.domain.BoardRepository;
import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    @Transactional
    public void create(BoardRequestDto dto) {
        boardRepository.save(
                new Board(dto.getTitle(), dto.getContent(), dto.getWriter())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> readAll() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> results = new ArrayList<>();

        boards.stream().forEach((e) -> {
            results.add(BoardResponseDto.builder()
                    .id(e.getId())
                    .title(e.getTitle())
                    .content(e.getContent())
                    .writer(e.getWriter())
                    .likeCount(e.getLikeCount())
                    .build()
            );
        });

        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponseDto readOne(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(NoSuchElementException::new);

        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .likeCount(board.getLikeCount())
                .build();
    }

    @Override
    @Transactional
    public void update(Long boardId, BoardRequestDto dto) {
        Board existBoard = boardRepository.findById(boardId)
                .orElseThrow(NoSuchElementException::new);

        existBoard.update(dto.getTitle(), dto.getContent(), dto.getWriter());
    }

    @Override
    @Transactional
    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
