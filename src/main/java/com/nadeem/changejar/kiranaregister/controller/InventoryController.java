package com.nadeem.changejar.kiranaregister.controller;

import com.nadeem.changejar.kiranaregister.entity.Inventory;
import com.nadeem.changejar.kiranaregister.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // get inventory for a product in a store
    @GetMapping("/{storeId}/{productId}")
    public ResponseEntity<?> getInventory(@PathVariable String storeId,
                                          @PathVariable String productId) {
        return inventoryService.getInventory(storeId, productId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
