package com.project.crud.reply.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
public class ReplyResponseDto {
    private Long id;
    private String content;
    private String writer;

    public ReplyResponseDto(Long id, String content, String writer) {
        this.id = id;
        this.content = content;
        this.writer = writer;
    }
}
