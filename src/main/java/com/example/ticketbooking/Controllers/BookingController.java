package com.example.ticketbooking.Controllers;

import com.example.ticketbooking.Entities.Ticket;
import com.example.ticketbooking.Entities.User;
import com.example.ticketbooking.dto.BookingRequestDto;
import com.example.ticketbooking.service.UserBookingService;
import com.example.ticketbooking.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private UserBookingService userBookingService;
    @Autowired
    User user;
    @Autowired
    UserService userService;

    @PostMapping("book-ticket")
    public ResponseEntity<Ticket> createBooking(@RequestBody BookingRequestDto bookingRequest, Authentication authentication) {  
        String userName = authentication.getName();
        user = userService.getUserByUserName(userName);

        Ticket newTicket = userBookingService.bookTicket(
                user.getId(), bookingRequest.getTrainId(),
                bookingRequest.getSource(), bookingRequest.getDestination());
        return ResponseEntity.ok(newTicket);
    }
    @GetMapping("/my-bookings")
    public ResponseEntity<List<Ticket>> getBookingHistory(Authentication authentication) {
        String userName = authentication.getName();
        user = userService.getUserByUserName(userName);
        List<Ticket> myBookings = userBookingService.getBookingHistory(user.getId());
        return new ResponseEntity<>(myBookings, HttpStatus.OK);
    }
    @DeleteMapping("/cancel-booking")
    public ResponseEntity<?> cancelBooking(@PathVariable String ticketId, Authentication authentication){
        user = userService.getUserByUserName(authentication.getName());
        userBookingService.cancelTicket(ticketId, user.getId());
        return new ResponseEntity<>("Booking with Ticket ID "+ ticketId + " cancelled successfully", HttpStatus.OK);
    }


}