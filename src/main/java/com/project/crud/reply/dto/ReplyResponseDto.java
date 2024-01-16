package com.project.crud.reply.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyResponseDto {
    private Long id;
    private String content;
    private String writer;
    private String createdDate;
    private String modifiedDate;

    @Builder
    ReplyResponseDto(Long id, String content, String writer, String createdDate, String modifiedDate) {
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
