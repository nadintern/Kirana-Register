package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.entity.Store;

import java.util.List;

public interface StoreService {

    Store createStore(String storeName, String region, String address, String baseCurrency);

    List<Store> getAllStores();

    List<Store> getStoresByUser(List<String> storeIds);
}
