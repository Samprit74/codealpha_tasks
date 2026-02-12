package com.hotel_booking.dto.response;

import com.hotel_booking.entity.enums.RoomType;

import java.math.BigDecimal;

public class RoomResponse {

    private Long id;
    private RoomType roomType;
    private BigDecimal pricePerNight;
    private String description;
    private String photoUrl;

    public RoomResponse(Long id,
                        RoomType roomType,
                        BigDecimal pricePerNight,
                        String description,
                        String photoUrl) {
        this.id = id;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public Long getId() { return id; }
    public RoomType getRoomType() { return roomType; }
    public BigDecimal getPricePerNight() { return pricePerNight; }
    public String getDescription() { return description; }
    public String getPhotoUrl() { return photoUrl; }
}
