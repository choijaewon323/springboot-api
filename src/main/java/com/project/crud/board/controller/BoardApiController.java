package com.project.crud.board.controller;

import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.board.service.BoardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.crud.common.ApiResponse.ok;
import static com.project.crud.common.ApiResponse.okWithBody;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/board")
public class BoardApiController {
    private static final int PAGING_SIZE = 20;
    private final BoardService boardService;

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

        List<BoardResponseDto> boards = boardService.readAllByPagingDesc(pageIndex, PAGING_SIZE);

        return okWithBody(boards);
    }

    @GetMapping("/list/covering/{pageIndex}")
    public ResponseEntity<List<BoardResponseDto>> readAllByPaging(@PathVariable @NotNull Integer pageIndex, @RequestParam(value = "order", required = false) String order) {
        checkInvalidIndex(pageIndex);

        List<BoardResponseDto> boards = boardService.readAllByPagingCovering(pageIndex, PAGING_SIZE);

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

    private void checkInvalidIndex(final int index) {
        if (isNegative(index)) {
            throw new IllegalArgumentException("페이지 번호는 음수가 될 수 없습니다");
        }
    }

    private boolean isNegative(final int number) {
        return number < 0;
    }
}
