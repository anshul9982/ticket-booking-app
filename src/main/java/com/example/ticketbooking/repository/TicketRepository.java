package com.example.ticketbooking.repository;

import com.example.ticketbooking.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    // You can add custom queries here later, e.g., find tickets by user
}