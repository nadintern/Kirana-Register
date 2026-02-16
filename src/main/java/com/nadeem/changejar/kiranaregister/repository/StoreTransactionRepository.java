package com.nadeem.changejar.kiranaregister.repository;

import com.nadeem.changejar.kiranaregister.entity.StoreTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface StoreTransactionRepository extends JpaRepository<StoreTransaction, UUID> {
    List<StoreTransaction> findByStoreId(String storeId);
    List<StoreTransaction> findByStoreIdAndCreatedAtBetween(String storeId, Instant start, Instant end);
}
