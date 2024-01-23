package com.project.crud.board.dto;

import com.project.crud.board.domain.Board;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private long likeCount;
    private long cnt;
    private String createdDate;
    private String modifiedDate;


    @Builder
    BoardResponseDto(Long id, String title, String content, String writer, long likeCount, long cnt, String createdDate, String modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.likeCount = likeCount;
        this.cnt = cnt;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static List<BoardResponseDto> toDtoList(List<Board> boards) {
        List<BoardResponseDto> results = new ArrayList<>();

        boards.stream().forEach(e -> results.add(e.toDto()));
        return results;
    }
}
