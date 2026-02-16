package com.nadeem.changejar.kiranaregister.controller.auth;

import com.nadeem.changejar.kiranaregister.dto.auth.*;
import com.nadeem.changejar.kiranaregister.service.auth.PublicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PublicController {

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    // register a new user
    @PostMapping("/public/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = publicService.registerUser(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // register a store owner with their stores
    @PostMapping("/register/store-owner")
    public ResponseEntity<?> registerStoreOwner(@RequestBody StoreOwnerRegisterRequest request) {
        try {
            StoreOwnerRegisterResponse response = publicService.registerStoreOwner(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // login with username and password
    @PostMapping("/public/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = publicService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // change password for a user
    @PutMapping("/change-password/{username}")
    public ResponseEntity<?> changePassword(@PathVariable String username,
                                            @RequestBody ChangePasswordRequest request) {
        try {
            ChangePasswordResponse response = publicService.changePassword(username, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // assign a role to a user (admin operation)
    @PutMapping("/assign-role")
    public ResponseEntity<?> assignRole(@RequestBody AssignRoleRequest request) {
        try {
            AssignRoleResponse response = publicService.assignRole(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
