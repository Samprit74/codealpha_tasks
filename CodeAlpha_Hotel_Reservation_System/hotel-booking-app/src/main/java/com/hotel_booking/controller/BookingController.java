package com.hotel_booking.controller;

import com.hotel_booking.dto.request.BookingRequest;
import com.hotel_booking.dto.response.BookingResponse;
import com.hotel_booking.entity.Booking;
import com.hotel_booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/{userId}/{roomId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingResponse> createBooking(
            @PathVariable Long userId,
            @PathVariable Long roomId,
            @Valid @RequestBody BookingRequest request) {

        Booking booking = bookingService.createBooking(
                userId,
                roomId,
                request.getCheckInDate(),
                request.getCheckOutDate(),
                request.getAdults(),
                request.getChildren()
        );

        return ResponseEntity.ok(mapToResponse(booking));
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {

        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/confirmation/{code}")
    public ResponseEntity<BookingResponse> getByConfirmationCode(
            @PathVariable String code) {

        Booking booking = bookingService.getBookingByConfirmationCode(code);
        return ResponseEntity.ok(mapToResponse(booking));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookingResponse>> getUserBookings(
            @PathVariable Long userId) {

        List<BookingResponse> bookings = bookingService
                .getUserBookings(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookings);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {

        List<BookingResponse> bookings = bookingService
                .getAllBookings()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookings);
    }

    private BookingResponse mapToResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getTotalGuests(),
                booking.getConfirmationCode()
        );
    }
}
