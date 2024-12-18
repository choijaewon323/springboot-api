package com.project.crud.report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByBoardIdAndAccountId(Long boardId, Long accountId);
    List<Report> findByBoardId(Long boardId);
    List<Report> findByAccountId(Long accountId);
}
