package com.project.crud.board.repository;

import com.project.crud.board.domain.QBoard;
import com.project.crud.board.dto.BoardListAndCountDto;
import com.project.crud.board.dto.BoardListDto;
import com.project.crud.tag.QTag;
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

    private static final QBoard board = QBoard.board;
    private static final QTag tag = QTag.tag;

    public BoardListAndCountDto findBoardListByPaging(Integer pageSize,
                                                      Integer pageIndex,
                                                      String keyword) {
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
                .join(tag)
                .on(board.id.eq(tag.boardId))
                .where(titleContains(keyword), contentContains(keyword), writerContains(keyword), tagContains(keyword))
                .orderBy(board.id.desc())
                .limit(pageSize)
                .offset((long) pageSize * pageIndex)
                .fetch();

        Long count = factory
                .select(board.count())
                .from(board)
                .join(tag)
                .on(board.id.eq(tag.boardId))
                .where(titleContains(keyword), contentContains(keyword), writerContains(keyword), tagContains(keyword))
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

    private BooleanExpression tagContains(String tagName) {
        if (tagName == null) {
            return null;
        }

        return tag.name.contains(tagName);
    }
}
