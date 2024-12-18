package com.project.crud.report;

import com.project.crud.account.domain.Account;
import com.project.crud.account.repository.AccountRepository;
import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.report.dto.ReportDto;
import com.project.crud.report.dto.ReportResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;
    private final ReportQueryRepository reportQueryRepository;

    public void reportBoard(ReportDto reportDto) {
        Account account = getAccount(reportDto.username());
        checkAlreadyExist(reportDto.boardId(), account.getId());

        Board board = getBoard(reportDto.boardId());

        reportRepository.save(Report.of(reportDto.boardId(), account.getId(), reportDto.content()));

        board.report();
    }

    @Transactional(readOnly = true)
    public List<ReportResponseDto> findByBoard(Long boardId) {
        return reportQueryRepository.findByBoard(boardId);
    }

    @Transactional(readOnly = true)
    public List<ReportResponseDto> findByUsername(String username) {
        return reportQueryRepository.findByUsername(username);
    }

    private void checkAlreadyExist(Long boardId, Long accountId) {
        Optional<Report> exist = reportRepository.findByBoardIdAndAccountId(boardId, accountId);

        if (exist.isPresent()) {
            throw new IllegalStateException("report : 이미 신고했습니다");
        }
    }

    private Account getAccount(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("report : username이 올바르지 않습니다"));
    }

    private Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("report : 해당 board가 없습니다"));
    }
}

