package com.project.crud.board.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.dto.*;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.board.repository.BoardSearchRepository;
import com.project.crud.tag.Tag;
import com.project.crud.tag.TagRepository;
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
    private final TagRepository tagRepository;

    public void create(BoardCreateWithTagDto dto) {
        Board board = boardRepository.save(Board.of(dto.title(), dto.content(), dto.writer()));

        tagRepository.saveAll(dto.tags().stream()
                .map(t -> Tag.of(t, board.getId()))
                .toList()
        );
    }

    @Transactional(readOnly = true)
    public BoardListAndCountDto searchByOption(Integer pageSize, Integer pageIndex, String keyword) {
        BoardListAndCountQueryDto result = boardSearchRepository.findBoardListByPaging(pageSize, pageIndex, keyword);

        List<BoardListDto> list = result.boards().stream()
                .map(b -> {
                    if (b.isBanned()) {
                        return BoardListDto.ofBanned(b);
                    }
                    return BoardListDto.of(b);
                })
                .toList();
        return new BoardListAndCountDto(result.count(), pageSize, list);
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> readAll() {
        final List<Board> boards = boardRepository.findAll();

        return boards.stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    public BoardResponseDto readOne(final Long boardId) {
        final Board board = findById(boardId);

        board.cntUp();

        return BoardResponseDto.toDto(board);
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
