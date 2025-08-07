package com.example.ticketbooking.repository;

import com.example.ticketbooking.Entities.Ticket;
import com.example.ticketbooking.Entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

    List<Ticket> findByUser(User user);
    
}