package com.nadeem.changejar.kiranaregister.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refund_items")
@Data
public class RefundItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refund_id", nullable = false)
    private Refund refund;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "quantity_refunded", nullable = false)
    private Integer quantityRefunded;

    @Column(name = "refund_amount_base", nullable = false, precision = 12, scale = 2)
    private BigDecimal refundAmountBase;

    @Column(name = "refund_amount_payment", nullable = false, precision = 12, scale = 2)
    private BigDecimal refundAmountPayment;

    @Column(name = "created_at")
    private Instant createdAt;
}
