package com.project.crud.board.repository;

import com.project.crud.board.domain.Board;
import com.project.crud.board.domain.QBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class BoardSearchRepository {

    private final JPAQueryFactory factory;

    public BoardSearchRepository(JPAQueryFactory factory) {
        this.factory = factory;
    }

    QBoard board = QBoard.board;

    public List<Board> findAllByPaging(final int pageIndex, final int pageSize) {
        List<Long> ids = factory
                .select(board.id)
                .from(board)
                .orderBy(board.id.desc())
                .limit(pageSize)
                .offset((long) pageIndex * pageSize)
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return List.of();
        }

        return factory
                .select(board)
                .from(board)
                .where(board.id.in(ids))
                .orderBy(board.id.desc())
                .fetch();
    }

    public List<Board> findAllByOptions(String title, String content, String writer) {
        return factory
                .select(board)
                .from(board)
                .where(titleContains(title), contentContains(content), writerContains(writer))
                .orderBy(board.id.desc())
                .fetch();
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
