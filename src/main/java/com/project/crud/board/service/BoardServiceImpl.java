package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import org.springframework.stereotype.Service;
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
        boardRepository.save(dto.toEntity());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> readAll() {
        List<Board> boards = boardRepository.findAll();

        return makeDtoList(boards);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponseDto readOne(Long boardId) {
        Board board = findExistBoard(boardId);

        return board.toDto();
    }

    @Override
    @Transactional
    public void update(Long boardId, BoardRequestDto dto) {
        Board existBoard = findExistBoard(boardId);

        existBoard.update(dto);
    }

    @Override
    @Transactional
    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    private Board findExistBoard(final Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(NoSuchElementException::new);

        return board;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByContent(String keyword) {
        List<Board> boards = boardRepository.searchByContent(keyword);

        return makeDtoList(boards);
    }

    private List<BoardResponseDto> makeDtoList(List<Board> boards) {
        List<BoardResponseDto> results = new ArrayList<>();

        boards.stream().forEach(e -> results.add(e.toDto()));
        return results;
    }
}
