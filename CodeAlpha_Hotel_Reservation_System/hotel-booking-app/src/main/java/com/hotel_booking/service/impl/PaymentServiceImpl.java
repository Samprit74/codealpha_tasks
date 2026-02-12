package com.hotel_booking.service.impl;

import com.hotel_booking.entity.Booking;
import com.hotel_booking.entity.Payment;
import com.hotel_booking.entity.enums.PaymentStatus;
import com.hotel_booking.exception.BadRequestException;
import com.hotel_booking.exception.ResourceNotFoundException;
import com.hotel_booking.repository.BookingRepository;
import com.hotel_booking.repository.PaymentRepository;
import com.hotel_booking.service.PaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Payment processPayment(Long bookingId,
                                  BigDecimal amount,
                                  String paymentMethod) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (paymentRepository.findByBookingId(bookingId).isPresent()) {
            throw new BadRequestException("Payment already processed for this booking");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Invalid payment amount");
        }

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.SUCCESS);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByBooking(Long bookingId) {

        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException("Booking not found");
        }

        return paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for this booking"));
    }
}
