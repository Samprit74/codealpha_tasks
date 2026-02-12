package com.hotel_booking.service;

import com.hotel_booking.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    Booking createBooking(Long userId,
                          Long roomId,
                          LocalDate checkIn,
                          LocalDate checkOut,
                          int adults,
                          int children);

    void cancelBooking(Long bookingId);

    List<Booking> getAllBookings();

    Booking getBookingByConfirmationCode(String confirmationCode);

    List<Booking> getUserBookings(Long userId);
}
