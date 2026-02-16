package com.nadeem.changejar.kiranaregister.repository;

import com.nadeem.changejar.kiranaregister.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
