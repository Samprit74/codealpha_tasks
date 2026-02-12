package com.hotel_booking.controller;

import com.hotel_booking.dto.request.LoginRequest;
import com.hotel_booking.dto.request.RegisterRequest;
import com.hotel_booking.dto.response.UserResponse;
import com.hotel_booking.entity.User;
import com.hotel_booking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());

        User savedUser = userService.register(user);

        return ResponseEntity.ok(
                new UserResponse(
                        savedUser.getId(),
                        savedUser.getEmail(),
                        savedUser.getName(),
                        savedUser.getPhoneNumber(),
                        savedUser.getRole()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request) {

        String token = userService.login(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(token);
    }
}
