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
    ReplyResponseDto(final Long id, final String content, final String writer, final String createdDate, final String modifiedDate) {
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
