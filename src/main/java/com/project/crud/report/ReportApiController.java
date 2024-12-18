package com.project.crud.report;

import com.project.crud.report.dto.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/report")
public class ReportApiController {
    private final ReportService reportService;

    @PostMapping("/board")
    public void reportBoard(@RequestBody ReportDto reportDto) {
        reportService.reportBoard(reportDto);
    }
}
