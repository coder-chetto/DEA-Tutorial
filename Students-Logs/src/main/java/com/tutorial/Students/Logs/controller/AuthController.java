package com.tutorial.Students.Logs.controller;

import com.tutorial.Students.Logs.dto.RegisterRequest;
import com.tutorial.Students.Logs.dto.RoleRequest;
import com.tutorial.Students.Logs.dto.UserResponse;
import com.tutorial.Students.Logs.entity.AppUser;
import com.tutorial.Students.Logs.entity.Role;
import com.tutorial.Students.Logs.service.UserService;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@Valid @RequestBody RoleRequest request) {
        Role role = userService.createRole(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        AppUser user = userService.registerUser(request.username(), request.password(), request.roles());
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        UserResponse response = new UserResponse(user.getId(), user.getUsername(), roles);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
