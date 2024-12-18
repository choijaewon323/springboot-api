package com.project.crud.reply.dto;

import com.project.crud.reply.domain.Reply;
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

    public static ReplyResponseDto of(Reply reply) {
        return ReplyResponseDto.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .writer(reply.getWriter())
                .createdDate(reply.getCreatedDate())
                .modifiedDate(reply.getModifiedDate())
                .build();
    }
}
