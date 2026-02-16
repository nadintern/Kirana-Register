package com.nadeem.changejar.kiranaregister.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "store_transactions")
@Data
public class StoreTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "transaction_type", nullable = false, length = 10)
    private String transactionType;

    @Column(name = "total_in_base_currency", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalInBaseCurrency;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(length = 15)
    private String status;

    @Column(name = "base_currency", nullable = false, length = 3)
    private String baseCurrency;
}
