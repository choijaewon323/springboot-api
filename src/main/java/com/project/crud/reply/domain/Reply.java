package com.project.crud.reply.domain;

import com.project.crud.common.TimeEntity;
import com.project.crud.exception.CustomException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.crud.common.StringValidator.checkNotBlankAndNotNull;
import static com.project.crud.exception.ErrorCode.BOARD_NOT_FOUND;
import static com.project.crud.exception.ErrorCode.REPLY_CREATION_ERROR;

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
        checkNotBlankAndNotNull(content, "reply : 내용이 없습니다");
        checkNotBlankAndNotNull(writer, "reply : 작성자가 없습니다");
        checkContentUnder300(content);
        checkBoardIdNull(boardId);

        this.content = content;
        this.writer = writer;
        this.boardId = boardId;
    }

    public void updateContent(String content) {
        checkNotBlankAndNotNull(content, "reply : 내용이 없습니다");
        checkContentUnder300(content);

        this.content = content;
    }

    public void updateWriter(String writer) {
        this.writer = writer;
    }

    private void checkContentUnder300(String content) {
        if (content.length() > 300) {
            throw new CustomException(REPLY_CREATION_ERROR, "댓글의 내용은 300자 이하여야 합니다");
        }
    }

    private void checkBoardIdNull(Long boardId) {
        if (boardId == null) {
            throw new CustomException(BOARD_NOT_FOUND, "댓글의 boardId가 null입니다");
        }
    }
}
