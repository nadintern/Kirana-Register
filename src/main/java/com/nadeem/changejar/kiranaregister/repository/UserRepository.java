package com.nadeem.changejar.kiranaregister.repository;

import com.nadeem.changejar.kiranaregister.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<Users, ObjectId> {

    Optional<Users> findByUserName(String userName);

    boolean existsByUserName(String userName);
}
