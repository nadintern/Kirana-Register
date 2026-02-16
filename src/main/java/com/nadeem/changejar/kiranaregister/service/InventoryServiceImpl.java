package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.entity.Inventory;
import com.nadeem.changejar.kiranaregister.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public Optional<Inventory> getInventory(String storeId, String productId) {
        // TODO: implement - look up inventory record
        return Optional.empty();
    }

    @Override
    public Inventory updateQuantity(String storeId, String productId, int quantityChange) {
        // TODO: implement - find or create inventory record, adjust quantity
        return null;
    }
}
