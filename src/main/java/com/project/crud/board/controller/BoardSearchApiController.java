package com.project.crud.board.controller;

import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.board.service.BoardSearchService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.project.crud.common.ApiResponse.okWithBody;

@Slf4j
@RestController
@RequestMapping("/api/v1/board/search")
public class BoardSearchApiController {
    private final BoardSearchService boardSearchService;

    public BoardSearchApiController(BoardSearchService boardSearchService) {
        this.boardSearchService = boardSearchService;
    }

    @GetMapping("/search")
    public List<BoardResponseDto> searchByOption(@RequestParam String title,
                                                 @RequestParam String content,
                                                 @RequestParam String writer) {
        return boardSearchService.searchByOption(title, content, writer);
    }

    @GetMapping("/content")
    public ResponseEntity<List<BoardResponseDto>> searchByContent(@RequestParam @NotNull String keyword) {
        List<BoardResponseDto> boards = boardSearchService.searchInContent(keyword);


        return okWithBody(boards);
    }

    @GetMapping("/content/fulltext")
    public ResponseEntity<List<BoardResponseDto>> searchByContentFullText(@RequestParam @NotNull String keyword) {
        List<BoardResponseDto> boards = boardSearchService.searchInContent(keyword);

        return okWithBody(boards);
    }

    @GetMapping("/writer")
    public ResponseEntity<List<BoardResponseDto>> searchByWriter(@RequestParam @NotNull(message = "작성자는 null이 될 수 없습니다")
                                                                         String keyword) {
        List<BoardResponseDto> boards = boardSearchService.searchInWriter(keyword);

        return okWithBody(boards);
    }

    @GetMapping("/title")
    public ResponseEntity<List<BoardResponseDto>> searchByTitle(@RequestParam @NotNull(message = "제목은 null이 될 수 없습니다")
                                                                        String keyword) {
        List<BoardResponseDto> boards = boardSearchService.searchInTitle(keyword);

        return okWithBody(boards);
    }
}
