package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.board.repository.BoardSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardSearchRepository boardSearchRepository;

    public void create(final BoardRequestDto dto) {
        boardRepository.save(dto.toEntity());
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByOption(Integer pageSize, Integer pageIndex, String title, String content, String writer) {
        return boardSearchRepository.findAllByOptionsAndPaging(pageSize, pageIndex, title, content, writer).stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    public List<BoardResponseDto> readAll() {
        final List<Board> boards = boardRepository.findAll();

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
