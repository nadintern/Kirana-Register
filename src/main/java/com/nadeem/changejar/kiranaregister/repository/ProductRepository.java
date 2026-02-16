package com.nadeem.changejar.kiranaregister.repository;

import com.nadeem.changejar.kiranaregister.entity.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {

    List<Product> findByStoreId(ObjectId storeId);

    List<Product> findByStoreIdAndIsActiveTrue(ObjectId storeId);
}
