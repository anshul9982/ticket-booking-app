package com.example.ticketbooking.dto;

import lombok.Data;

@Data
public class BookingRequestDto {
    private Long userId;
    private String trainId;
    private String source;
    private String destination;
}