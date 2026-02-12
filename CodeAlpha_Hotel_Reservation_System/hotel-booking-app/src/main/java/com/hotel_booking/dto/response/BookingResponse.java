package com.hotel_booking.dto.response;

import java.time.LocalDate;

public class BookingResponse {

    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalGuests;
    private String confirmationCode;

    public BookingResponse(Long id,
                           LocalDate checkInDate,
                           LocalDate checkOutDate,
                           int totalGuests,
                           String confirmationCode) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalGuests = totalGuests;
        this.confirmationCode = confirmationCode;
    }

    public Long getId() { return id; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public int getTotalGuests() { return totalGuests; }
    public String getConfirmationCode() { return confirmationCode; }
}
