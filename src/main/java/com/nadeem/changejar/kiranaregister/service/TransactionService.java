package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.dto.transaction.CreateTransactionRequest;
import com.nadeem.changejar.kiranaregister.dto.transaction.CreateTransactionResponse;
import com.nadeem.changejar.kiranaregister.entity.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface TransactionService {

    // create a transaction with items, currency conversion, and store-level entries
    CreateTransactionResponse createTransaction(CreateTransactionRequest request);

    // find a transaction by its ID
    Optional<Transaction> getTransactionById(UUID transactionId);
}
