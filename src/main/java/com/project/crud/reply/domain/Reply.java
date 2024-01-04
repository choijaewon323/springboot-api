package com.project.crud.reply.domain;

import com.project.crud.board.domain.Board;
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
    @Column(name = "REPLY_ID")
    private Long id;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "WRITER")
    private String writer;

    @ManyToOne
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
}
