package com.hotel_booking.service;

import com.hotel_booking.entity.Payment;

import java.math.BigDecimal;

public interface PaymentService {

    Payment processPayment(Long bookingId,
                           BigDecimal amount,
                           String paymentMethod);

    Payment getPaymentByBooking(Long bookingId);
}
