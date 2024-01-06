package com.project.crud.reply.dto;

import com.project.crud.board.domain.Board;
import com.project.crud.reply.domain.Reply;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class ReplyRequestDto {
    @NotNull
    private String content;
    @NotNull
    private String writer;

    public ReplyRequestDto(String content, String writer) {
        this.content = content;
        this.writer = writer;
    }

    public Reply toEntity(Board board) {
        Objects.requireNonNull(board);

        return new Reply(content, writer, board);
    }
}
