package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void create(final BoardRequestDto dto) {
        boardRepository.save(dto.toEntity());
    }

    public List<BoardResponseDto> readAll() {
        final List<Board> boards = boardRepository.findAll();

        return boards.stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    public List<BoardResponseDto> readAllByPagingDesc(final int index, final int size) {
        final List<Board> boards = boardRepository.findAllByPaging(index, size);

        return boards.stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    public List<BoardResponseDto> readAllByPagingCovering(final int index, final int size) {
        final List<Board> boards = boardRepository.findAllByPaging(index, size);

        return boards.stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    public BoardResponseDto readOne(final Long boardId) {
        final Board board = findById(boardId);

        board.cntUp();

        return board.toDto();
    }

    public void update(Long boardId, BoardRequestDto dto) {
        final Board existBoard = findById(boardId);

        existBoard.update(dto);
    }

    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    private Board findById(final Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("해당 게시물이 없습니다"));
    }
}
