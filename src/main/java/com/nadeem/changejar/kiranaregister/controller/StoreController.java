package com.nadeem.changejar.kiranaregister.controller;

import com.nadeem.changejar.kiranaregister.dto.store.*;
import com.nadeem.changejar.kiranaregister.entity.Store;
import com.nadeem.changejar.kiranaregister.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // create a new store
    @PostMapping
    public ResponseEntity<?> createStore(@Valid @RequestBody CreateStoreRequest request) {
        try {
            Store store = storeService.createStore(
                    request.getStoreName(),
                    request.getRegion(),
                    request.getAddress(),
                    request.getBaseCurrency()
            );

            CreateStoreResponse response = CreateStoreResponse.builder()
                    .success(true)
                    .storeId(store.getId().toHexString())
                    .storeName(store.getStoreName())
                    .region(store.getRegion())
                    .address(store.getAddress())
                    .baseCurrency(store.getBaseCurrency())
                    .createdAt(store.getCreatedAt())
                    .updatedAt(store.getUpdatedAt())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get all stores
    @GetMapping
    public ResponseEntity<GetStoreResponse> getAllStores() {
        List<Store> stores = storeService.getAllStores();

        List<StoreData> storeDataList = stores.stream()
                .map(s -> StoreData.builder()
                        .storeId(s.getId().toHexString())
                        .storeName(s.getStoreName())
                        .region(s.getRegion())
                        .address(s.getAddress())
                        .baseCurrency(s.getBaseCurrency())
                        .build())
                .toList();

        return ResponseEntity.ok(GetStoreResponse.builder()
                .success(true)
                .data(storeDataList)
                .build());
    }

    // get stores for a user by their store IDs
    @GetMapping("/user")
    public ResponseEntity<GetStoreResponse> getStoresByUser(@RequestParam List<String> storeIds) {
        List<Store> stores = storeService.getStoresByUser(storeIds);

        List<StoreData> storeDataList = stores.stream()
                .map(s -> StoreData.builder()
                        .storeId(s.getId().toHexString())
                        .storeName(s.getStoreName())
                        .region(s.getRegion())
                        .address(s.getAddress())
                        .baseCurrency(s.getBaseCurrency())
                        .build())
                .toList();

        return ResponseEntity.ok(GetStoreResponse.builder()
                .success(true)
                .data(storeDataList)
                .build());
    }
}
