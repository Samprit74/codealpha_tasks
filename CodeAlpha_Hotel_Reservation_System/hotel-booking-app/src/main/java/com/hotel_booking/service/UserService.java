package com.hotel_booking.service;

import com.hotel_booking.entity.Booking;
import com.hotel_booking.entity.User;

import java.util.List;

public interface UserService {

    User register(User user);

    String login(String email, String password);

    User getUserById(Long id);

    List<User> getAllUsers();

    void deleteUser(Long id);

    User getLoggedInUser();

    List<Booking> getUserBookingHistory(Long userId);
}
