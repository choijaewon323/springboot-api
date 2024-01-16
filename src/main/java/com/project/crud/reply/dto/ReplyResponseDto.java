package com.project.crud.reply.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyResponseDto {
    private Long id;
    private String content;
    private String writer;

    @Builder
    ReplyResponseDto(Long id, String content, String writer) {
        this.id = id;
        this.content = content;
        this.writer = writer;
    }
}
