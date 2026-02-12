package com.hotel_booking.dto.response;

import com.hotel_booking.entity.enums.Role;

public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private Role role;

    public UserResponse(Long id,
                        String email,
                        String name,
                        String phoneNumber,
                        Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public Role getRole() { return role; }
}
