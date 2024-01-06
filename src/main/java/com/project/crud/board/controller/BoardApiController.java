package com.project.crud.board.controller;

import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.board.service.BoardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
@CrossOrigin(origins = "http://localhost:3000")
public class BoardApiController {
    private final BoardService boardService;

    public BoardApiController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> findOne(@NotNull @PathVariable Long boardId) {
        BoardResponseDto board = boardService.readOne(boardId);

        return ResponseEntity
                .ok()
                .body(board);
    }

    @GetMapping()
    public ResponseEntity<List<BoardResponseDto>> findAll() {
        List<BoardResponseDto> boards = boardService.readAll();

        return ResponseEntity
                .ok()
                .body(boards);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody BoardRequestDto dto) {
        boardService.create(dto);

        return ResponseEntity
                .ok()
                .build();
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<Void> update(@PathVariable Long boardId, @Valid @RequestBody BoardRequestDto dto) {
        boardService.update(boardId, dto);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long boardId) {
        boardService.delete(boardId);

        return ResponseEntity
                .ok()
                .build();
    }
}
