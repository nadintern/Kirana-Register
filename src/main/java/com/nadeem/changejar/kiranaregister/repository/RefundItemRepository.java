package com.nadeem.changejar.kiranaregister.repository;

import com.nadeem.changejar.kiranaregister.entity.RefundItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RefundItemRepository extends JpaRepository<RefundItem, UUID> {
    List<RefundItem> findByRefundId(UUID refundId);
}
