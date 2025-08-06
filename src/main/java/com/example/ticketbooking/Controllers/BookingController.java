package com.example.ticketbooking.Controllers;

import com.example.ticketbooking.Entities.Ticket;
import com.example.ticketbooking.dto.BookingRequestDto;
import com.example.ticketbooking.service.UserBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private UserBookingService userBookingService;

    @PostMapping
    public ResponseEntity<Ticket> createBooking(@RequestBody BookingRequestDto bookingRequest) {
        Ticket newTicket = userBookingService.bookTicket(
                bookingRequest.getUserId(), bookingRequest.getTrainId(),
                bookingRequest.getSource(), bookingRequest.getDestination());
        return ResponseEntity.ok(newTicket);
    }
}