package com.hotel_booking.repository;

import com.hotel_booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {


    Optional<Booking> findByConfirmationCode(String confirmationCode);


    List<Booking> findByUserId(Long userId);
}
