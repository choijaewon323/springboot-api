package com.project.crud.board.controller;

import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.board.service.BoardSearchService;
import com.project.crud.board.service.BoardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/board")
public class BoardApiController {
    private final int PAGING_SIZE = 20;
    private final BoardService boardService;
    private final BoardSearchService boardSearchService;

    public BoardApiController(BoardService boardService, BoardSearchService boardSearchService) {
        this.boardService = boardService;
        this.boardSearchService = boardSearchService;
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> findOne(@NotNull(message = "boardId는 null이 될 수 없습니다") @PathVariable Long boardId) {
        BoardResponseDto board = boardService.readOne(boardId);


        return okWithBody(board);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDto>> findAll() {
        List<BoardResponseDto> boards = boardService.readAll();


        return okWithBody(boards);
    }

    @GetMapping("/list/{pageIndex}")
    public ResponseEntity<List<BoardResponseDto>> findAllByPaging(@PathVariable @NotNull Integer pageIndex, @RequestParam(value = "order", required = false) String order) {
        checkInvalidIndex(pageIndex);

        List<BoardResponseDto> results = boardService.readAllByPagingDesc(pageIndex, PAGING_SIZE);


        return okWithBody(results);
    }

    @GetMapping("/list/covering/{pageIndex}")
    public ResponseEntity<List<BoardResponseDto>> readAllByPaging(@PathVariable @NotNull Integer pageIndex, @RequestParam(value = "order", required = false) String order) {
        checkInvalidIndex(pageIndex);

        List<BoardResponseDto> results = boardService.readAllByPagingCovering(pageIndex, PAGING_SIZE);


        return okWithBody(results);
    }

    @GetMapping("/search/content")
    public ResponseEntity<List<BoardResponseDto>> searchByContent(@RequestParam @NotNull String keyword) {
        List<BoardResponseDto> boards = boardSearchService.searchByContent(keyword);


        return okWithBody(boards);
    }

    @GetMapping("/search/writer")
    public ResponseEntity<List<BoardResponseDto>> searchByWriter(@RequestParam @NotNull(message = "작성자는 null이 될 수 없습니다")
                                                                 String keyword) {
        List<BoardResponseDto> boards = boardSearchService.searchByWriter(keyword);


        return okWithBody(boards);
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<BoardResponseDto>> searchByTitle(@RequestParam @NotNull(message = "제목은 null이 될 수 없습니다")
                                                                String keyword) {
        List<BoardResponseDto> boards = boardSearchService.searchByTitle(keyword);


        return okWithBody(boards);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody BoardRequestDto dto) {
        boardService.create(dto);


        return ok();
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<Void> update(@NotNull @PathVariable Long boardId, @Valid @RequestBody BoardRequestDto dto) {
        boardService.update(boardId, dto);


        return ok();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long boardId) {
        boardService.delete(boardId);


        return ok();
    }

    private ResponseEntity<Void> ok() {
        return ResponseEntity
                .ok()
                .build();
    }

    private <T> ResponseEntity<T> okWithBody(T body) {
        return ResponseEntity
                .ok()
                .body(body);
    }

    private void checkInvalidIndex(final int index) {
        if (isNegative(index)) {
            throw new IllegalArgumentException("페이지 번호는 음수가 될 수 없습니다");
        }
    }

    private boolean isNegative(final int number) {
        return number < 0;
    }
}
