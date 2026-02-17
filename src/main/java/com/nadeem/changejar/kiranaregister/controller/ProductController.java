package com.nadeem.changejar.kiranaregister.controller;

import com.nadeem.changejar.kiranaregister.dto.product.*;
import com.nadeem.changejar.kiranaregister.entity.Product;
import com.nadeem.changejar.kiranaregister.entity.Store;
import com.nadeem.changejar.kiranaregister.service.ProductService;
import com.nadeem.changejar.kiranaregister.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final StoreService storeService;

    // create a product under a store
    @PostMapping
    public ResponseEntity<?> createProduct(@PathVariable String storeId,
                                           @Valid @RequestBody CreateProductRequest request) {
        try {
            ObjectId storeObjectId = new ObjectId(storeId);

            Product product = productService.createProduct(
                    storeObjectId,
                    request.getProductName(),
                    request.getCategory(),
                    request.getDisplayPrice(),
                    request.getQuantity()
            );

            // fetch store to get currency
            List<Store> stores = storeService.getStoresByUser(List.of(storeId));
            String currency = stores.isEmpty() ? "N/A" : stores.getFirst().getBaseCurrency();

            CreateProductResponse response = CreateProductResponse.builder()
                    .success(true)
                    .productId(product.getId().toHexString())
                    .storeId(storeId)
                    .productName(product.getProductName())
                    .category(product.getCategory())
                    .displayPrice(product.getDisplayPrice())
                    .storesCurrency(currency)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get all active products for a store
    @GetMapping
    public ResponseEntity<List<GetProductResponse>> getProducts(@PathVariable String storeId) {
        ObjectId storeObjectId = new ObjectId(storeId);
        List<Product> products = productService.getProductsByStore(storeObjectId);

        List<GetProductResponse> response = products.stream()
                .map(p -> GetProductResponse.builder()
                        .productId(p.getId().toHexString())
                        .productName(p.getProductName())
                        .category(p.getCategory())
                        .displayPrice(p.getDisplayPrice())
                        .isActive(p.isActive())
                        .quantity(p.getQuantity())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }

    // update a product
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String storeId,
                                           @PathVariable String productId,
                                           @Valid @RequestBody UpdateProductRequest request) {
        try {
            ObjectId productObjectId = new ObjectId(productId);

            Product updated = productService.updateProduct(
                    productObjectId,
                    request.getProductName(),
                    request.getDisplayPrice(),
                    request.getCategory()
            );

            UpdateProductResponse response = UpdateProductResponse.builder()
                    .success(true)
                    .productId(updated.getId().toHexString())
                    .storeId(storeId)
                    .productName(updated.getProductName())
                    .displayPrice(updated.getDisplayPrice())
                    .isActive(updated.isActive())
                    .currentStock(updated.getQuantity())
                    .updatedAt(updated.getUpdatedAt())
                    .build();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
