package com.project.crud.board.controller;

import com.project.crud.board.dto.BoardListAndCountDto;
import com.project.crud.board.dto.BoardListDto;
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

    @GetMapping("/search")
    public BoardListAndCountDto searchByOption(@RequestParam(required = false) String title,
                                               @RequestParam(required = false) String content,
                                               @RequestParam(required = false) String writer,
                                               @RequestParam(required = false) Integer pageIndex) {
        checkInvalidIndex(pageIndex);

        return boardService.searchByOption(PAGING_SIZE,
                pageIndex,
                ifBlankReturnNull(title),
                ifBlankReturnNull(content),
                ifBlankReturnNull(writer));
    }

    private void checkInvalidIndex(Integer index) {
        if (index == null) {
            return;
        }

        if (isNegative(index)) {
            throw new IllegalArgumentException("페이지 번호는 음수가 될 수 없습니다");
        }
    }

    private boolean isNegative(final int number) {
        return number < 0;
    }

    private String ifBlankReturnNull(String str) {
        if (str.isBlank()) {
            return null;
        }
        return str;
    }
}
