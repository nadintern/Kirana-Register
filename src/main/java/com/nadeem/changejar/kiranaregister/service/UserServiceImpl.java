package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.entity.Users;
import com.nadeem.changejar.kiranaregister.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Users createUser(String userName, String passwordHash, String role) {
        if (userRepository.existsByUserName(userName)) {
            throw new RuntimeException("Username already exists: " + userName);
        }
        Users user = new Users();
        user.setUserName(userName);
        user.setPasswordHash(passwordHash);
        user.setRoles(List.of(role));
        return userRepository.save(user);
    }

    @Override
    public Optional<Users> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public Users assignRole(String userName, String role) {
        Users user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found: " + userName));
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
        }
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(String userName, String newPasswordHash) {
        Users user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found: " + userName));
        user.setPasswordHash(newPasswordHash);
        userRepository.save(user);
    }
}
