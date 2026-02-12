package com.hotel_booking.repository;

import com.hotel_booking.entity.Room;
import com.hotel_booking.entity.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {


    List<Room> findByRoomType(RoomType roomType);


    @Query("""
        SELECT r FROM Room r
        WHERE r.id NOT IN (
            SELECT b.room.id FROM Booking b
            WHERE (:checkIn <= b.checkOutDate)
            AND (:checkOut >= b.checkInDate)
        )
    """)
    List<Room> findAvailableRooms(
            LocalDate checkIn,
            LocalDate checkOut
    );


    @Query("""
        SELECT r FROM Room r
        WHERE r.roomType = :roomType
        AND r.id NOT IN (
            SELECT b.room.id FROM Booking b
            WHERE (:checkIn <= b.checkOutDate)
            AND (:checkOut >= b.checkInDate)
        )
    """)
    List<Room> findAvailableRoomsByType(
            RoomType roomType,
            LocalDate checkIn,
            LocalDate checkOut
    );
}
