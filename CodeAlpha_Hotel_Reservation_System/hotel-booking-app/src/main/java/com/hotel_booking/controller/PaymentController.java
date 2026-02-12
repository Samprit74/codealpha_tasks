package com.hotel_booking.controller;

import com.hotel_booking.dto.request.PaymentRequest;
import com.hotel_booking.dto.response.PaymentResponse;
import com.hotel_booking.entity.Payment;
import com.hotel_booking.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentResponse> processPayment(
            @PathVariable Long bookingId,
            @Valid @RequestBody PaymentRequest request) {

        Payment payment = paymentService.processPayment(
                bookingId,
                request.getAmount(),
                request.getPaymentMethod()
        );

        return ResponseEntity.ok(mapToResponse(payment));
    }

    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentResponse> getPaymentByBooking(
            @PathVariable Long bookingId) {

        Payment payment = paymentService.getPaymentByBooking(bookingId);
        return ResponseEntity.ok(mapToResponse(payment));
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getPaymentMethod(),
                payment.getPaymentDate()
        );
    }
}
