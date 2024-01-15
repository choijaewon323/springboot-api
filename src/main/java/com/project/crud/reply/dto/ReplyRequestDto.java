package com.project.crud.reply.dto;

import com.project.crud.board.domain.Board;
import com.project.crud.reply.domain.Reply;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor
public class ReplyRequestDto {
    @NotBlank
    private String content;
    @NotBlank
    private String writer;

    public ReplyRequestDto(String content, String writer) {
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
