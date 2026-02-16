package com.nadeem.changejar.kiranaregister.repository;

import com.nadeem.changejar.kiranaregister.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RefundRepository extends JpaRepository<Refund, UUID> {
    List<Refund> findByTransactionId(String transactionId);
}
