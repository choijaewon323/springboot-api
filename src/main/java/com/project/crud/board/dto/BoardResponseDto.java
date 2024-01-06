package com.project.crud.board.dto;

import com.project.crud.board.domain.Board;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private Long likeCount;

    public static BoardResponseDto toDto(Board board) {
        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .likeCount(board.getLikeCount())
                .build();
    }
}
