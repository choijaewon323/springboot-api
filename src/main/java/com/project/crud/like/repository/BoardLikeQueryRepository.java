package com.project.crud.like.repository;

import com.project.crud.like.domain.BoardLike;
import com.project.crud.like.domain.QBoardLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BoardLikeQueryRepository {
    private final JPAQueryFactory factory;

    private static final QBoardLike boardLike = QBoardLike.boardLike;

    public Optional<BoardLike> findBoardLikeIfPresent(Long boardId, Long accountId) {
        BoardLike like = factory
                .select(boardLike)
                .from(boardLike)
                .where(boardLike.boardId.eq(boardId).and(boardLike.accountId.eq(accountId)))
                .fetchOne();

        if (like == null) {
            return Optional.empty();
        }

        return Optional.of(like);
    }
}
