package com.example.ticketbooking.dto;

import lombok.Data;

@Data
public class BookingRequestDto {
    private String trainId;
    private String source;
    private String destination;
}