package com.hotel_booking.dto.response;

import com.hotel_booking.entity.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {

    private Long id;
    private BigDecimal amount;
    private PaymentStatus status;
    private String paymentMethod;
    private LocalDateTime paymentDate;

    public PaymentResponse(Long id,
                           BigDecimal amount,
                           PaymentStatus status,
                           String paymentMethod,
                           LocalDateTime paymentDate) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }

    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
}
