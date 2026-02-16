package com.nadeem.changejar.kiranaregister.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "products")
@Data
public class Product {

    @Id
    private ObjectId id;

    @Field("store_id")
    private ObjectId storeId;

    @Field("product_name")
    private String productName;

    private String category;

    @Field("display_price")
    private BigDecimal displayPrice;

    private int quantity;

    @Field("is_active")
    private boolean isActive = true;

    @CreatedDate
    @Field("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Instant updatedAt;
}
