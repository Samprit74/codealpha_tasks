package com.hotel_booking.controller;

import com.hotel_booking.dto.response.BookingResponse;
import com.hotel_booking.dto.response.UserResponse;
import com.hotel_booking.entity.Booking;
import com.hotel_booking.entity.User;
import com.hotel_booking.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(
                new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getPhoneNumber(),
                        user.getRole()
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getPhoneNumber(),
                        user.getRole()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getLoggedInUser() {

        User user = userService.getLoggedInUser();

        return ResponseEntity.ok(
                new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getPhoneNumber(),
                        user.getRole()
                )
        );
    }

    @GetMapping("/{id}/bookings")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getUserBookingHistory(
            @PathVariable Long id) {

        List<BookingResponse> bookings = userService
                .getUserBookingHistory(id)
                .stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookings);
    }

    private BookingResponse mapToBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getTotalGuests(),
                booking.getConfirmationCode()
        );
    }
}
