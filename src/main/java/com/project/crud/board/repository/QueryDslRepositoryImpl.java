package com.project.crud.board.repository;

import com.project.crud.board.domain.Board;
import com.project.crud.board.domain.QBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QueryDslRepositoryImpl implements QueryDslRepository {

    private final JPAQueryFactory factory;

    public QueryDslRepositoryImpl(JPAQueryFactory factory) {
        this.factory = factory;
    }

    QBoard board = QBoard.board;

    @Override
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

}
