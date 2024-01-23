package com.project.crud.board.service;

import com.project.crud.board.dto.BoardResponseDto;

import java.util.List;

public interface BoardSearchService {
    List<BoardResponseDto> searchByContent(final String keyword);

    List<BoardResponseDto> searchByTitle(final String keyword);

    List<BoardResponseDto> searchByWriter(final String keyword);

    List<BoardResponseDto> searchByContentFullText(final String keyword);
}
