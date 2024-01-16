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
    public List<BoardResponseDto> searchByContent(String keyword) {
        List<Board> boards = boardRepository.searchByContent(keyword);

        return makeDtoList(boards);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByTitle(String title) {
        List<Board> boards = boardRepository.findByTitleContaining(title);

        return makeDtoList(boards);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByWriter(String writer) {
        List<Board> boards = boardRepository.findByWriterContaining(writer);

        return makeDtoList(boards);
    }

    private List<BoardResponseDto> makeDtoList(List<Board> boards) {
        List<BoardResponseDto> results = new ArrayList<>();

        boards.stream().forEach(e -> results.add(e.toDto()));
        return results;
    }
}
