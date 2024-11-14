package com.project.crud.reply.domain;

import com.project.crud.common.TimeEntity;
import com.project.crud.reply.dto.ReplyRequestDto;
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

    @Column(name = "BOARD_ID", nullable = false)
    private Long boardId;

    @Builder
    public Reply(final String content, final String writer, final Long boardId) {
        checkContentUnder300(content);
        checkBoardIdNull(boardId);

        this.content = content;
        this.writer = writer;
        this.boardId = boardId;
    }

    private void checkContentUnder300(String content) {
        if (content.length() > 300) {
            throw new IllegalStateException("댓글의 내용은 300자 이하여야 합니다");
        }
    }

    private void checkBoardIdNull(Long boardId) {
        if (boardId == null) {
            throw new IllegalStateException("댓글의 boardId가 null입니다");
        }
    }

    public void update(final ReplyRequestDto dto) {
        this.content = dto.getContent();
    }
}
