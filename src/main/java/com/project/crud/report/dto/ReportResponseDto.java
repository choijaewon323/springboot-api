package com.project.crud.report.dto;

public record ReportResponseDto(
        Long boardId,
        String username,
        String content
) {
}
