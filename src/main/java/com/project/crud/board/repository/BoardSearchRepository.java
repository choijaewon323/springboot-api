package com.project.crud.board.repository;

import com.project.crud.board.domain.Board;
import com.project.crud.board.domain.QBoard;
import com.project.crud.board.dto.BoardListAndCountQueryDto;
import com.project.crud.tag.QTag;
import com.querydsl.core.BooleanBuilder;
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

    private static final QBoard board = QBoard.board;
    private static final QTag tag = QTag.tag;

    public BoardListAndCountQueryDto findBoardListByPaging(Integer pageSize,
                                                           Integer pageIndex,
                                                           String keyword) {
        if (pageIndex == null) {
            pageIndex = 0;
        } else {
            pageIndex--;
        }

        List<Board> boards = factory
                .select(board)
                .from(board)
                .leftJoin(tag)
                .on(board.id.eq(tag.boardId))
                .where(searchConditions(keyword))
                .orderBy(board.id.desc())
                .limit(pageSize)
                .offset((long) pageSize * pageIndex)
                .fetch();

        Long count = factory
                .select(board.count())
                .from(board)
                .leftJoin(tag)
                .on(board.id.eq(tag.boardId))
                .where(searchConditions(keyword))
                .fetchOne();

        Objects.requireNonNull(count, "board의 count가 null입니다");

        return new BoardListAndCountQueryDto(count, boards);
    }

    private BooleanBuilder searchConditions(String keyword) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (keyword == null) {
            return booleanBuilder;
        }

        booleanBuilder.or(titleContains(keyword));
        booleanBuilder.or(contentContains(keyword));
        booleanBuilder.or(writerContains(keyword));
        booleanBuilder.or(tagContains(keyword));

        return booleanBuilder;
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

    private BooleanExpression tagContains(String tagName) {
        if (tagName == null) {
            return null;
        }

        return tag.name.contains(tagName);
    }


}
