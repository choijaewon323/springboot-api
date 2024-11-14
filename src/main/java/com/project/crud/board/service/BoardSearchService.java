package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.board.repository.BoardSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardSearchService {
    private final BoardRepository boardRepository;
    private final BoardSearchRepository boardSearchRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchInContent(final String keyword) {
        List<Board> boards = boardRepository.findByContentFulltext(keyword);

        return boards.stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchInTitle(final String keyword) {
        List<Board> boards = boardRepository.findByTitleContaining(keyword);

        return boards.stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchInWriter(final String keyword) {
        List<Board> boards = boardRepository.findByWriterContaining(keyword);

        return boards.stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    public List<BoardResponseDto> searchByOption(String title, String content, String writer) {
        return boardSearchRepository.findAllByOptions(title, content, writer).stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }
}
