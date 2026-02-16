package com.nadeem.changejar.kiranaregister.service.auth;

import com.nadeem.changejar.kiranaregister.dto.auth.*;
import com.nadeem.changejar.kiranaregister.entity.Store;
import com.nadeem.changejar.kiranaregister.entity.Users;
import com.nadeem.changejar.kiranaregister.repository.StoreRepository;
import com.nadeem.changejar.kiranaregister.repository.UserRepository;
import com.nadeem.changejar.kiranaregister.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PublicService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public PublicService(UserRepository userRepository,
                         StoreRepository storeRepository,
                         PasswordEncoder passwordEncoder,
                         JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // register a basic user with USER role
    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByUserName(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }

        Users user = new Users();
        user.setUserName(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of("USER"));
        user.setActive(true);

        Users saved = userRepository.save(user);

        return RegisterResponse.builder()
                .message("User registered successfully")
                .userId(saved.getId().toHexString())
                .username(saved.getUserName())
                .roles(saved.getRoles())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    // register a store owner: creates user + stores, links them together
    public StoreOwnerRegisterResponse registerStoreOwner(StoreOwnerRegisterRequest request) {
        if (userRepository.existsByUserName(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }

        // create each store and collect IDs
        List<String> storeIds = new ArrayList<>();
        Map<String, String> storeRoles = new HashMap<>();
        List<StoreOwnerRegisterResponse.StoreRoleInfo> responseRoles = new ArrayList<>();

        for (StoreOwnerRegisterRequest.StoreInfo storeInfo : request.getStores()) {
            Store store = new Store();
            store.setStoreName(storeInfo.getStoreName());
            store.setRegion(storeInfo.getRegion());
            store.setAddress(storeInfo.getAddress());
            store.setBaseCurrency(storeInfo.getBaseCurrency());
            Store savedStore = storeRepository.save(store);

            storeIds.add(savedStore.getId().toHexString());

            storeRoles.put(savedStore.getId().toHexString(), "OWNER");

            responseRoles.add(StoreOwnerRegisterResponse.StoreRoleInfo.builder()
                    .storeId(savedStore.getId().toHexString())
                    .role("OWNER")
                    .build());
        }

        // create user with STORE_OWNER role
        Users user = new Users();
        user.setUserName(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of("STORE_OWNER"));
        user.setStoreIds(storeIds);
        user.setStoreRoles(storeRoles);
        user.setActive(true);

        userRepository.save(user);

        return StoreOwnerRegisterResponse.builder()
                .message("Store owner registered successfully")
                .username(request.getUsername())
                .role("STORE_OWNER")
                .storeRoles(responseRoles)
                .build();
    }

    // validate credentials and return login response
    public LoginResponse login(LoginRequest request) {
        Users user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getId());

        return LoginResponse.builder()
                .accessToken(token)
                .expiresInSeconds(8400)
                .build();
    }

    // change a user's password
    public ChangePasswordResponse changePassword(String username, ChangePasswordRequest request) {
        Users user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return ChangePasswordResponse.builder()
                .message("Password changed successfully")
                .build();
    }

    // assign a role to a user
    public AssignRoleResponse assignRole(AssignRoleRequest request) {
        Users user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getUsername()));

        if (!user.getRoles().contains(request.getRole())) {
            user.getRoles().add(request.getRole());
            userRepository.save(user);
        }

        return AssignRoleResponse.builder()
                .message("Role assigned successfully")
                .username(request.getUsername())
                .role(request.getRole())
                .build();
    }
}
