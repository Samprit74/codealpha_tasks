package com.hotel_booking.service;

import com.hotel_booking.entity.Room;
import com.hotel_booking.entity.enums.RoomType;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    Room addRoom(RoomType roomType,
                 BigDecimal pricePerNight,
                 String description,
                 MultipartFile photo);

    Room updateRoom(Long id,
                    RoomType roomType,
                    BigDecimal pricePerNight,
                    String description,
                    MultipartFile photo);

    void deleteRoom(Long id);

    Room getRoomById(Long id);

    List<Room> getAllRooms();

    List<Room> getAvailableRooms(LocalDate checkIn,
                                 LocalDate checkOut);

    List<Room> getAvailableRoomsByType(RoomType roomType,
                                       LocalDate checkIn,
                                       LocalDate checkOut);
}
