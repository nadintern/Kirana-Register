package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.entity.Store;
import com.nadeem.changejar.kiranaregister.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public Store createStore(String storeName, String region, String address, String baseCurrency) {
        if (storeRepository.existsByStoreName(storeName)) {
            throw new RuntimeException("Store already exists: " + storeName);
        }
        Store store = new Store();
        store.setStoreName(storeName);
        store.setRegion(region);
        store.setAddress(address);
        store.setBaseCurrency(baseCurrency);
        return storeRepository.save(store);
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public List<Store> getStoresByUser(List<String> storeIds) {
        List<ObjectId> objectIds = storeIds.stream()
                .map(ObjectId::new)
                .toList();
        return storeRepository.findAllById(objectIds);
    }
}
