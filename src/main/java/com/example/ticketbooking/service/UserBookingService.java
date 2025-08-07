package com.example.ticketbooking.service;

import com.example.ticketbooking.Entities.Seat;
import com.example.ticketbooking.Entities.Ticket;
import com.example.ticketbooking.Entities.Train;
import com.example.ticketbooking.Entities.User;
import com.example.ticketbooking.repository.SeatRepository;
import com.example.ticketbooking.repository.TicketRepository;
import com.example.ticketbooking.repository.TrainRepository;
import com.example.ticketbooking.repository.UserRepository;
import com.example.ticketbooking.exception.InvalidStationException;
import com.example.ticketbooking.exception.NoSeatsAvailableException;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserBookingService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    User user;
    @Autowired
    Train train;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    Seat seat;


    @Transactional
    public Ticket bookTicket(Long userId, String trainId, String source, String destination) {
        user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        train = trainRepository.findById(trainId)
                .orElseThrow(() -> new EntityNotFoundException("Train not found with ID: " + trainId));

        if (!train.getStations().contains(source) || !train.getStations().contains(destination) || train.getStations().indexOf(source) >= train.getStations().indexOf(destination)) {
            throw new InvalidStationException("Invalid source or destination for the selected train route.");
        }

        Seat availableSeat = seatRepository.findAndLockFirstAvailableSeatByTrain(trainId)
                .orElseThrow(() -> new NoSeatsAvailableException("No available seats on train " + trainId));

        availableSeat.setBooked(true);

        Ticket ticket = new Ticket();

        ticket.setTicketId(UUID.randomUUID().toString());
        ticket.setUser(user);
        ticket.setTrain(train);
        ticket.setSeat(availableSeat);
        ticket.setSource(source);
        ticket.setDestination(destination);
        ticket.setDateOfTravel(LocalDate.now());

        return ticketRepository.save(ticket);

    }
    public List<Ticket> getBookingHistory(Long userId) {
        user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        return ticketRepository.findByUser(user);
    }
    @Transactional
    public void cancelTicket(String ticketId, Long userId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with ID: " + ticketId));

        // Security check: Ensure the user owns the ticket they are trying to cancel.
        if (!ticket.getUser().getId().equals(userId)) {
            throw new SecurityException("User is not authorized to cancel this ticket.");
        }

        // Release the seat
        Seat seat = ticket.getSeat();
        seat.setBooked(false);
        seatRepository.save(seat);

        // Delete the ticket
        ticketRepository.delete(ticket);
    }
}
