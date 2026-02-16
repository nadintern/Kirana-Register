package com.nadeem.changejar.kiranaregister.controller;

import com.nadeem.changejar.kiranaregister.dto.report.GetReportResponse;
import com.nadeem.changejar.kiranaregister.entity.Report;
import com.nadeem.changejar.kiranaregister.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // get all reports for a store
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<GetReportResponse>> getReportsByStore(@PathVariable String storeId) {
        List<Report> reports = reportService.getReportsByStore(storeId);
        List<GetReportResponse> response = reports.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    // get reports for a store filtered by type
    @GetMapping("/store/{storeId}/type/{reportType}")
    public ResponseEntity<List<GetReportResponse>> getReportsByStoreAndType(
            @PathVariable String storeId,
            @PathVariable String reportType) {
        List<Report> reports = reportService.getReportsByStoreAndType(storeId, reportType);
        List<GetReportResponse> response = reports.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    // map Report entity to DTO
    private GetReportResponse toResponse(Report r) {
        return GetReportResponse.builder()
                .reportId(r.getId())
                .storeId(r.getStoreId())
                .reportType(r.getReportType())
                .periodStart(r.getPeriodStart())
                .periodEnd(r.getPeriodEnd())
                .baseCurrency(r.getBaseCurrency())
                .totalCredits(r.getTotalCredits())
                .totalDebits(r.getTotalDebits())
                .netFlow(r.getNetFlow())
                .status(r.getStatus())
                .createdAt(r.getCreatedAt())
                .completedAt(r.getCompletedAt())
                .build();
    }
}
