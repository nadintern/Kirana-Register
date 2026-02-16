package com.nadeem.changejar.kiranaregister.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "stores")
@Data
public class Store {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @Field("store_name")
    private String storeName;

    private String region;

    private String address;

    @Field("base_currency")
    private String baseCurrency;

    @CreatedDate
    @Field("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Instant updatedAt;
}
