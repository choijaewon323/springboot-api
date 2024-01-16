package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
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
    public List<BoardResponseDto> readAllByPagingDesc(final int index, final int size) {
        PageRequest pageRequest = PageRequest.of(index, size, Sort.by(Sort.Direction.DESC, "created_date"));

        Page<Board> boards = boardRepository.findAll(pageRequest);

        List<BoardResponseDto> results = new ArrayList<>();
        boards.getContent().stream().forEach(e -> {
            results.add(BoardResponseDto.builder()
                            .id(e.getId())
                            .title(e.getTitle())
                            .content(e.getContent())
                            .writer(e.getWriter())
                            .likeCount(e.getLikeCount())
                            .cnt(e.getCnt())
                            .createdDate(e.getCreatedDate())
                            .modifiedDate(e.getModifiedDate())
                    .build());
        });

        return results;
    }

    @Override
    public BoardResponseDto readOne(Long boardId) {
        Board board = findExistBoard(boardId);

        board.cntUp();

        return board.toDto();
    }

    @Override
    public void update(Long boardId, BoardRequestDto dto) {
        Board existBoard = findExistBoard(boardId);

        existBoard.update(dto);
    }

    @Override
    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    private Board findExistBoard(final Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("해당 게시물이 없습니다"));

        return board;
    }

    private Board findExistBoardByPessimistic(final Long boardId) {
        Board board = boardRepository.pessimisticFindById(boardId)
                .orElseThrow(() -> new NoSuchElementException("해당 게시물이 없습니다"));

        return board;
    }

    private List<BoardResponseDto> makeDtoList(List<Board> boards) {
        List<BoardResponseDto> results = new ArrayList<>();

        boards.stream().forEach(e -> results.add(e.toDto()));
        return results;
    }
}
