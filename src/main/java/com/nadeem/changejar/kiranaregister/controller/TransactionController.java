package com.nadeem.changejar.kiranaregister.controller;

import com.nadeem.changejar.kiranaregister.dto.transaction.CreateTransactionRequest;
import com.nadeem.changejar.kiranaregister.dto.transaction.CreateTransactionResponse;
import com.nadeem.changejar.kiranaregister.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // create a new transaction
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestParam String userId,
                                               @RequestBody CreateTransactionRequest request) {
        // TODO: get userId from auth context instead of param
        CreateTransactionResponse response = transactionService.createTransaction(userId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // get a transaction by ID
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable UUID transactionId) {
        return transactionService.getTransactionById(transactionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
