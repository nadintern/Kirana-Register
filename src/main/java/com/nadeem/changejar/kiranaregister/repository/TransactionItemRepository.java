package com.nadeem.changejar.kiranaregister.repository;

import com.nadeem.changejar.kiranaregister.entity.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, Integer> {
    List<TransactionItem> findByTransactionId(UUID transactionId);
}
