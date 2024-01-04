package com.project.crud.reply.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReplyRequestDto {
    private String content;
    private String writer;

    public ReplyRequestDto(String content, String writer) {
        this.content = content;
        this.writer = writer;
    }
}
