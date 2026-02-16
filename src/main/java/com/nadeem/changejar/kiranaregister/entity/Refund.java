package com.nadeem.changejar.kiranaregister.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "refunds")
@Data
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "refund_amount_payment", nullable = false, precision = 15, scale = 2)
    private BigDecimal refundAmountPayment;

    @Column(name = "payment_currency", nullable = false, length = 3)
    private String paymentCurrency;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(nullable = false, length = 15)
    private String status;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "refund", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefundItem> items = new ArrayList<>();
}
