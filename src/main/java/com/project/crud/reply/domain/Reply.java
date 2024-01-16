package com.project.crud.reply.domain;

import com.project.crud.board.domain.Board;
import com.project.crud.common.TimeEntity;
import com.project.crud.reply.dto.ReplyRequestDto;
import com.project.crud.reply.dto.ReplyResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "REPLY")
public class Reply extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPLY_ID", nullable = false)
    private Long id;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "WRITER", nullable = false)
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    @Builder
    public Reply(String content, String writer, Board board) {
        this.content = content;
        this.writer = writer;
        this.board = board;
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
