package com.project.crud.board.service;

import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;

import java.util.List;

public interface BoardService {
    void create(BoardRequestDto dto);

    List<BoardResponseDto> readAll();

    List<BoardResponseDto> readAllByPagingDesc(final int index, final int size);

    BoardResponseDto readOne(Long boardId);

    void update(Long boardId, BoardRequestDto dto);

    void delete(Long boardId);

    List<BoardResponseDto> searchByContent(String keyword);
}
