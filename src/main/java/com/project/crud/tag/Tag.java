package com.project.crud.tag;

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
public class Tag {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    private Tag(String name, Long boardId) {
        checkName(name);
        checkBoardId(boardId);

        this.name = name;
        this.boardId = boardId;
    }

    public static Tag of(String name, Long boardId) {
        return new Tag(name, boardId);
    }

    public void updateName(String name) {
        this.name = name;
    }

    private void checkName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("tag: name이 없습니다");
        }
    }

    private void checkBoardId(Long boardId) {
        if (boardId == null) {
            throw new IllegalArgumentException("tag: boardId가 null입니다");
        }

        if (boardId < 0) {
            throw new IllegalArgumentException("tag: boardId는 음수일 수 없습니다");
        }
    }
}
