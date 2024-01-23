package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardSearchServiceImpl implements BoardSearchService {
    private final BoardRepository boardRepository;

    public BoardSearchServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByContent(final String keyword) {
        List<Board> boards = boardRepository.searchByContent(keyword);

        return BoardResponseDto.toDtoList(boards);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByTitle(final String title) {
        List<Board> boards = boardRepository.findByTitleContaining(title);

        return BoardResponseDto.toDtoList(boards);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByWriter(final String writer) {
        List<Board> boards = boardRepository.findByWriterContaining(writer);

        return BoardResponseDto.toDtoList(boards);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByContentFullText(final String keyword) {
        List<Board> boards = boardRepository.searchByContentFullText(keyword);

        return BoardResponseDto.toDtoList(boards);
    }
}
