package com.project.crud.report;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    private String content;

    private Report(Long boardId, Long accountId, String content) {
        checkId(boardId);
        checkId(accountId);
        checkContent(content);

        this.boardId = boardId;
        this.accountId = accountId;
        this.content = content;
    }

    public static Report of(Long boardId, Long accountId, String content) {
        return new Report(boardId, accountId, content);
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("report : id가 null입니다");
        }

        if (id < 0) {
            throw new IllegalArgumentException("report : id는 음수일 수 없습니다");
        }
    }

    private void checkContent(String content) {
        if (content == null) {
            throw new IllegalArgumentException("report : 신고 내용이 없습니다");
        }

        if (content.isBlank()) {
            throw new IllegalArgumentException("report : 신고 내용이 없습니다");
        }
    }
}
