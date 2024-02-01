package com.project.crud.board.domain;

import com.project.crud.account.domain.Account;
import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.common.TimeEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "BOARD")
public class Board extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID", nullable = false)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    @Lob
    private String content;

    @Column(name = "WRITER", nullable = false)
    private String writer;

    @Column(name = "LIKE_COUNT")
    private long likeCount = 0L;

    @Column(name = "CNT")
    private long cnt = 0L;

    @Builder
    public Board(final String title, final String content, final String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void update(final BoardRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void updateWriter(final String writer) {
        this.writer = writer;
    }

    public void likeUp() {
        likeCount++;
    }

    public void likeDown() {
        if (likeCount == 0L) {
            throw new IllegalStateException("좋아요 개수는 음수가 될 수 없습니다");
        }

        likeCount--;
    }

    public void cntUp() {
        cnt++;
    }

    public BoardResponseDto toDto() {
        return BoardResponseDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .writer(writer)
                .likeCount(likeCount)
                .cnt(cnt)
                .createdDate(getCreatedDate())
                .modifiedDate(getModifiedDate())
                .build();
    }
}
