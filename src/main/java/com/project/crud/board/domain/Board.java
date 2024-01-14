package com.project.crud.board.domain;

import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Lock;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "BOARD")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void update(BoardRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.writer = dto.getWriter();
    }

    public void updateWriter(String writer) {
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
                .build();
    }
}
