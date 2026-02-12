package com.hotel_booking.service.impl;

import com.hotel_booking.entity.Room;
import com.hotel_booking.entity.enums.RoomType;
import com.hotel_booking.exception.BadRequestException;
import com.hotel_booking.exception.ResourceNotFoundException;
import com.hotel_booking.repository.RoomRepository;
import com.hotel_booking.service.CloudinaryService;
import com.hotel_booking.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final CloudinaryService cloudinaryService;

    public RoomServiceImpl(RoomRepository roomRepository,
                           CloudinaryService cloudinaryService) {
        this.roomRepository = roomRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public Room addRoom(RoomType roomType,
                        BigDecimal pricePerNight,
                        String description,
                        MultipartFile photo) {

        if (photo == null || photo.isEmpty()) {
            throw new BadRequestException("Room photo is required");
        }

        String photoUrl = cloudinaryService.uploadFile(photo);

        Room room = new Room();
        room.setRoomType(roomType);
        room.setPricePerNight(pricePerNight);
        room.setDescription(description);
        room.setPhotoUrl(photoUrl);

        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long id,
                           RoomType roomType,
                           BigDecimal pricePerNight,
                           String description,
                           MultipartFile photo) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        if (photo != null && !photo.isEmpty()) {
            String photoUrl = cloudinaryService.uploadFile(photo);
            room.setPhotoUrl(photoUrl);
        }

        room.setRoomType(roomType);
        room.setPricePerNight(pricePerNight);
        room.setDescription(description);

        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Long id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        roomRepository.delete(room);
    }

    @Override
    public Room getRoomById(Long id) {

        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getAvailableRooms(LocalDate checkIn,
                                        LocalDate checkOut) {

        if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            throw new BadRequestException("Invalid date range");
        }

        return roomRepository.findAvailableRooms(checkIn, checkOut);
    }

    @Override
    public List<Room> getAvailableRoomsByType(RoomType roomType,
                                              LocalDate checkIn,
                                              LocalDate checkOut) {

        if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            throw new BadRequestException("Invalid date range");
        }

        return roomRepository.findAvailableRoomsByType(roomType, checkIn, checkOut);
    }
}
