package com.project.crud.reply.dto;

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
    ReplyRequestDto(String content, String writer) {
        this.content = content;
        this.writer = writer;
    }

    public Reply toEntity(Board board) {
        return Reply.builder()
                .content(content)
                .writer(writer)
                .board(board)
                .build();
    }
}
