package com.hotel_booking.service.impl;

import com.hotel_booking.entity.Booking;
import com.hotel_booking.entity.Room;
import com.hotel_booking.entity.User;
import com.hotel_booking.exception.BadRequestException;
import com.hotel_booking.exception.ResourceNotFoundException;
import com.hotel_booking.repository.BookingRepository;
import com.hotel_booking.repository.RoomRepository;
import com.hotel_booking.repository.UserRepository;
import com.hotel_booking.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              UserRepository userRepository,
                              RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Booking createBooking(Long userId,
                                 Long roomId,
                                 LocalDate checkIn,
                                 LocalDate checkOut,
                                 int adults,
                                 int children) {

        if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            throw new BadRequestException("Invalid booking dates");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        boolean roomUnavailable = roomRepository
                .findAvailableRooms(checkIn, checkOut)
                .stream()
                .noneMatch(r -> r.getId().equals(roomId));

        if (roomUnavailable) {
            throw new BadRequestException("Room is not available for selected dates");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        booking.setNumberOfAdults(adults);
        booking.setNumberOfChildren(children);
        booking.setConfirmationCode(generateConfirmationCode());

        return bookingRepository.save(booking);
    }

    @Override
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        bookingRepository.delete(booking);
    }

    @Override
    public Booking getBookingByConfirmationCode(String confirmationCode) {

        return bookingRepository.findByConfirmationCode(confirmationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    @Override
    public List<Booking> getUserBookings(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        return bookingRepository.findByUserId(userId);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    private String generateConfirmationCode() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10)
                .toUpperCase();
    }
}
