package com.project.crud.reply.domain;

import com.project.crud.board.domain.Board;
import com.project.crud.reply.dto.ReplyRequestDto;
import com.project.crud.reply.dto.ReplyResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "REPLY")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REPLY_ID", nullable = false)
    private Long id;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "WRITER", nullable = false)
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    public Reply(String content, String writer, Board board) {
        this.content = content;
        this.writer = writer;
        this.board = board;
    }

    public void update(String content, String writer) {
        this.content = content;
        this.writer = writer;
    }

    public void update(ReplyRequestDto dto) {
        this.content = dto.getContent();
        this.writer = dto.getWriter();
    }

    public ReplyResponseDto toDto() {
        return ReplyResponseDto.builder()
                .id(id)
                .content(content)
                .writer(writer)
                .build();
    }
}
