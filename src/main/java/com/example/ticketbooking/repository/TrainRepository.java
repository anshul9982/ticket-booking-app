package com.example.ticketbooking.repository;

import com.example.ticketbooking.Entities.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train, String> {
    // You can add custom queries here later, e.g., find by train number
}