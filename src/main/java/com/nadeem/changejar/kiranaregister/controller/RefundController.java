package com.nadeem.changejar.kiranaregister.controller;

import com.nadeem.changejar.kiranaregister.dto.refund.*;
import com.nadeem.changejar.kiranaregister.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    // create a refund request
    @PostMapping
    public ResponseEntity<?> createRefund(@Valid @RequestBody CreateRefundRequest request) {
        CreateRefundResponse response = refundService.createRefund(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // approve or reject a refund
    @PutMapping("/{refundId}/status")
    public ResponseEntity<?> updateRefundStatus(@PathVariable UUID refundId,
                                                @Valid @RequestBody RefundStatusRequest request) {
        RefundApprovalResponse response = refundService.updateRefundStatus(refundId, request);
        return ResponseEntity.ok(response);
    }

    // get all refunds for a transaction
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<List<CreateRefundResponse>> getRefundsByTransaction(
            @PathVariable String transactionId) {
        List<CreateRefundResponse> response = refundService.getRefundsByTransaction(transactionId);
        return ResponseEntity.ok(response);
    }
}
