package com.project.crud.reply.dto;

import com.project.crud.account.domain.Account;
import com.project.crud.board.domain.Board;
import com.project.crud.reply.domain.Reply;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyRequestDto {
    @NotBlank
    private String content;
    @NotBlank
    private String writer;

    @Builder
    ReplyRequestDto(final String content, final String writer) {
        this.content = content;
        this.writer = writer;
    }

    public Reply toEntity(final Long boardId) {
        return Reply.builder()
                .content(content)
                .writer(writer)
                .boardId(boardId)
                .build();
    }
}
