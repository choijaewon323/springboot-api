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
    private long likeCount;
    private long cnt;
    private String createdDate;
    private String modifiedDate;
}
