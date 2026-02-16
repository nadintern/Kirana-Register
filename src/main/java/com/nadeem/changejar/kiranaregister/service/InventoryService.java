package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.entity.Inventory;

import java.util.Optional;

public interface InventoryService {

    // get current inventory for a product in a store
    Optional<Inventory> getInventory(String storeId, String productId);

    // update inventory quantity (called during transactions and refunds)
    Inventory updateQuantity(String storeId, String productId, int quantityChange);
}
