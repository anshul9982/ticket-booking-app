package com.example.ticketbooking.Entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Ticket {

    @Id
    private String ticketId;
    private String source;
    private String destination;
    private LocalDate dateOfTravel;

    @ManyToOne
    private User user;

    @ManyToOne
    private Train train;

    // A ticket is for one specific seat
    @OneToOne
    private Seat seat;
}
