package com.project.crud.like.controller;

import com.project.crud.like.domain.BoardLike;
import com.project.crud.like.dto.BoardLikeRequestDto;
import com.project.crud.like.service.BoardLikeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.crud.common.ApiResponse.ok;

@RestController
@RequestMapping("/api/v1/board/like")
public class BoardLikeApiController {
    private final BoardLikeService boardLikeService;

    public BoardLikeApiController(BoardLikeService boardLikeService) {
        this.boardLikeService = boardLikeService;
    }

    @PostMapping
    public ResponseEntity<Void> likeUp(@Valid @RequestBody BoardLikeRequestDto dto) {
        boardLikeService.up(dto);

        return ok();
    }

    @DeleteMapping
    public ResponseEntity<Void> likeDown(@Valid @RequestBody BoardLikeRequestDto dto) {
        boardLikeService.down(dto);

        return ok();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandle(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
