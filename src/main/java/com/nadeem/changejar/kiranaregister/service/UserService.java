package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.entity.Users;

import java.util.Optional;

public interface UserService {

    Users createUser(String userName, String passwordHash, String role);

    Optional<Users> findByUserName(String userName);

    boolean existsByUserName(String userName);

    Users assignRole(String userName, String role);

    void updatePassword(String userName, String newPasswordHash);
}
