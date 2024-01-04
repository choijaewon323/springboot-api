package com.project.crud.reply.controller;

import com.project.crud.reply.dto.ReplyRequestDto;
import com.project.crud.reply.dto.ReplyResponseDto;
import com.project.crud.reply.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/reply")
public class ReplyApiController {
    private final ReplyService replyService;

    public ReplyApiController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<Void> create(@PathVariable Long boardId, @RequestBody ReplyRequestDto dto) {
        replyService.create(boardId, dto);

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/list/{boardId}")
    public ResponseEntity<List<ReplyResponseDto>> readAll(@PathVariable Long boardId) {
        List<ReplyResponseDto> results = replyService.readAll(boardId);

        return ResponseEntity
                .ok()
                .body(results);
    }

    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyResponseDto> readOne(@PathVariable Long replyId) {
        ReplyResponseDto result = replyService.readOne(replyId);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<Void> update(@PathVariable Long replyId, @RequestBody ReplyRequestDto dto) {
        replyService.update(replyId, dto);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> delete(@PathVariable Long replyId) {
        replyService.delete(replyId);

        return ResponseEntity
                .ok()
                .build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
