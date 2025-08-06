package com.example.ticketbooking.repository;

import com.example.ticketbooking.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom query methods here, e.g.:
    // Optional<User> findByName(String name);
}