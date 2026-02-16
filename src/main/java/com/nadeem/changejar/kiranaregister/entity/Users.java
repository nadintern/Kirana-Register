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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "users")
@Data
public class Users {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @Field("user_name")
    private String userName;

    @Field("password_hash")
    private String passwordHash;

    private List<String> roles = new ArrayList<>();

    @Field("store_ids")
    private List<String> storeIds = new ArrayList<>();

    @Field("store_roles")
    private Map<String, String> storeRoles = new HashMap<>();

    @Field("is_active")
    private boolean isActive = true;

    @CreatedDate
    @Field("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Instant updatedAt;
}
