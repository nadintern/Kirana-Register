package com.nadeem.changejar.kiranaregister.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transaction_items")
@Data
public class TransactionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price_base", precision = 12, scale = 2)
    private BigDecimal unitPriceBase;

    @Column(name = "base_currency", length = 3)
    private String baseCurrency;

    @Column(name = "conversion_rate", precision = 12, scale = 6)
    private BigDecimal conversionRate;

    @Column(name = "unit_price_payment", precision = 12, scale = 2)
    private BigDecimal unitPricePayment;

    @Column(name = "total_base", precision = 15, scale = 2)
    private BigDecimal totalBase;

    @Column(name = "total_payment", precision = 15, scale = 2)
    private BigDecimal totalPayment;

    @Column(name = "created_at")
    private Instant createdAt;
}
