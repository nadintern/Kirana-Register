package com.nadeem.changejar.kiranaregister.dto.report;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
public class GetReportResponse {
    private String reportId;
    private String storeId;
    private String reportType;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private String baseCurrency;
    private BigDecimal totalCredits;
    private BigDecimal totalDebits;
    private BigDecimal netFlow;
    private String status;
    private Instant createdAt;
    private Instant completedAt;
}
