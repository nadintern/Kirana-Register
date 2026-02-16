package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.entity.Product;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Product createProduct(ObjectId storeId, String productName, String category, BigDecimal displayPrice, int quantity);

    List<Product> getProductsByStore(ObjectId storeId);

    Product updateProduct(ObjectId productId, String productName, BigDecimal displayPrice, String category);
}
