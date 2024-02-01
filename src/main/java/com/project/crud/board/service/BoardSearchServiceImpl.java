package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoardSearchServiceImpl implements BoardSearchService {
    private final BoardRepository boardRepository;

    public BoardSearchServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchInContent(final String keyword) {
        List<Board> boards = boardRepository.findByContentFulltext(keyword);

        return BoardResponseDto.toDtoList(boards);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchInTitle(final String keyword) {
        List<Board> boards = boardRepository.findByTitleContaining(keyword);

        return BoardResponseDto.toDtoList(boards);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchInWriter(final String keyword) {
        List<Board> boards = boardRepository.findByWriterContaining(keyword);

        return BoardResponseDto.toDtoList(boards);
    }
}
