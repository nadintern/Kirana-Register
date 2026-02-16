package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.entity.Product;
import com.nadeem.changejar.kiranaregister.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(ObjectId storeId, String productName, String category, BigDecimal displayPrice, int quantity) {
        Product product = new Product();
        product.setStoreId(storeId);
        product.setProductName(productName);
        product.setCategory(category);
        product.setDisplayPrice(displayPrice);
        product.setQuantity(quantity);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProductsByStore(ObjectId storeId) {
        return productRepository.findByStoreIdAndIsActiveTrue(storeId);
    }

    @Override
    public Product updateProduct(ObjectId productId, String productName, BigDecimal displayPrice, String category) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
        if (productName != null) {
            product.setProductName(productName);
        }
        if (displayPrice != null) {
            product.setDisplayPrice(displayPrice);
        }
        if (category != null) {
            product.setCategory(category);
        }
        return productRepository.save(product);
    }
}
