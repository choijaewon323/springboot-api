package com.project.crud.board.repository;

import com.project.crud.board.domain.QBoard;
import com.project.crud.board.dto.BoardListAndCountDto;
import com.project.crud.board.dto.BoardListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class BoardSearchRepository {

    private final JPAQueryFactory factory;

    public BoardSearchRepository(JPAQueryFactory factory) {
        this.factory = factory;
    }

    QBoard board = QBoard.board;

    public BoardListAndCountDto findBoardListByPaging(Integer pageSize,
                                                      Integer pageIndex,
                                                      String title,
                                                      String content,
                                                      String writer) {
        if (pageIndex == null) {
            pageIndex = 0;
        } else {
            pageIndex--;
        }

        List<BoardListDto> boardList = factory
                .select(Projections.constructor(BoardListDto.class,
                        board.id,
                        board.title,
                        board.likeCount))
                .from(board)
                .where(titleContains(title), contentContains(content), writerContains(writer))
                .orderBy(board.id.desc())
                .limit(pageSize)
                .offset((long) pageSize * pageIndex)
                .fetch();

        Long count = factory
                .select(board.count())
                .from(board)
                .where(titleContains(title), contentContains(content), writerContains(writer))
                .fetchOne();

        Objects.requireNonNull(count, "board의 count가 null입니다");

        return new BoardListAndCountDto(
                count, pageSize, boardList
        );
    }

    private BooleanExpression titleContains(String title) {
        if (title == null) {
            return null;
        }

        return board.title.contains(title);
    }

    private BooleanExpression contentContains(String content) {
        if (content == null) {
            return null;
        }

        return board.content.contains(content);
    }

    private BooleanExpression writerContains(String writer) {
        if (writer == null) {
            return null;
        }

        return board.writer.contains(writer);
    }
}
