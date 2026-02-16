package com.nadeem.changejar.kiranaregister.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Document(collection = "reports")
@Data
public class Report {

    @Id
    private String id;

    @Field("store_id")
    private String storeId;

    @Field("report_type")
    private String reportType;

    @Field("period_start")
    private LocalDate periodStart;

    @Field("period_end")
    private LocalDate periodEnd;

    @Field("base_currency")
    private String baseCurrency;

    @Field("total_credits")
    private BigDecimal totalCredits;

    @Field("total_debits")
    private BigDecimal totalDebits;

    @Field("net_flow")
    private BigDecimal netFlow;

    private String status;

    @Field("completed_at")
    private Instant completedAt;

    @CreatedDate
    @Field("created_at")
    private Instant createdAt;
}
