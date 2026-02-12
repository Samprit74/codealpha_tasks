package com.hotel_booking.controller;

import com.hotel_booking.dto.response.RoomResponse;
import com.hotel_booking.entity.Room;
import com.hotel_booking.entity.enums.RoomType;
import com.hotel_booking.service.RoomService;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> addRoom(
            @RequestParam RoomType roomType,
            @RequestParam BigDecimal pricePerNight,
            @RequestParam String description,
            @RequestParam MultipartFile photo) {

        Room room = roomService.addRoom(
                roomType,
                pricePerNight,
                description,
                photo
        );

        return ResponseEntity.ok(mapToResponse(room));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable Long id,
            @RequestParam RoomType roomType,
            @RequestParam BigDecimal pricePerNight,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile photo) {

        Room room = roomService.updateRoom(
                id,
                roomType,
                pricePerNight,
                description,
                photo
        );

        return ResponseEntity.ok(mapToResponse(room));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {

        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long id) {

        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(mapToResponse(room));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {

        List<RoomResponse> rooms = roomService.getAllRooms()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        List<RoomResponse> rooms = roomService
                .getAvailableRooms(checkIn, checkOut)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/available/type")
    public ResponseEntity<List<RoomResponse>> getAvailableRoomsByType(
            @RequestParam RoomType roomType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        List<RoomResponse> rooms = roomService
                .getAvailableRoomsByType(roomType, checkIn, checkOut)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(rooms);
    }

    private RoomResponse mapToResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getRoomType(),
                room.getPricePerNight(),
                room.getDescription(),
                room.getPhotoUrl()
        );
    }
}
