package com.project.crud.board.service;

import com.project.crud.board.dto.BoardResponseDto;

import java.util.List;

public interface BoardSearchService {
    List<BoardResponseDto> searchInContent(final String keyword);

    List<BoardResponseDto> searchInTitle(final String keyword);

    List<BoardResponseDto> searchInWriter(final String keyword);
}
