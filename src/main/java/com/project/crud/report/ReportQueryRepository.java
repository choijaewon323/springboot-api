package com.project.crud.report;

import com.project.crud.account.domain.QAccount;
import com.project.crud.board.domain.QBoard;
import com.project.crud.report.dto.ReportResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReportQueryRepository {
    private final JPAQueryFactory factory;

    private static final QReport report = QReport.report;
    private static final QBoard board = QBoard.board;
    private static final QAccount account = QAccount.account;

    public List<ReportResponseDto> findByBoard(Long boardId) {
        return factory
                .select(Projections.constructor(
                        ReportResponseDto.class,
                        report.boardId,
                        account.username,
                        report.content
                ))
                .from(report)
                .join(account).on(report.accountId.eq(account.id))
                .where(report.boardId.eq(boardId))
                .fetch();
    }

    public List<ReportResponseDto> findByUsername(String username) {
        return factory
                .select(Projections.constructor(
                        ReportResponseDto.class,
                        report.boardId,
                        account.username,
                        report.content
                ))
                .from(report)
                .join(account).on(report.accountId.eq(account.id))
                .where(account.username.eq(username))
                .fetch();
    }


}
