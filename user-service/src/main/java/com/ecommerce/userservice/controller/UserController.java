package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.UserRegistrationRequest;
import com.ecommerce.userservice.dto.UserResponse;
import com.ecommerce.userservice.mapper.UserMapper;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody UserRegistrationRequest request) {

        User saved = userService.register(request);
        return ResponseEntity.ok(UserMapper.toResponse(saved));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> findByEmail(@PathVariable String email) {

        return userService.findByEmail(email)
                .map(UserMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUsers() {

        return ResponseEntity.ok(
                userService.findAll()
                        .stream()
                        .map(UserMapper::toResponse)
                        .toList()
        );
    }
}