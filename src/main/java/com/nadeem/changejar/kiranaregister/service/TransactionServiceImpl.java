package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.dto.transaction.CreateTransactionRequest;
import com.nadeem.changejar.kiranaregister.dto.transaction.CreateTransactionResponse;
import com.nadeem.changejar.kiranaregister.entity.Transaction;
import com.nadeem.changejar.kiranaregister.repository.StoreTransactionRepository;
import com.nadeem.changejar.kiranaregister.repository.TransactionItemRepository;
import com.nadeem.changejar.kiranaregister.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionItemRepository transactionItemRepository;
    private final StoreTransactionRepository storeTransactionRepository;

    @Override
    public CreateTransactionResponse createTransaction(String userId, CreateTransactionRequest request) {
        // TODO: implement - fetch product prices, convert currencies,
        //  save transaction + items + store_transactions, update inventory
        return null;
    }

    @Override
    public Optional<Transaction> getTransactionById(UUID transactionId) {
        // TODO: implement - return transaction with items
        return Optional.empty();
    }
}
