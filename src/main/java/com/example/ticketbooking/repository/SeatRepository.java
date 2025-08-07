package com.example.ticketbooking.repository;

import com.example.ticketbooking.Entities.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.train.trainId = :trainId AND s.isBooked = false ORDER BY s.id LIMIT 1")
    Optional<Seat> findAndLockFirstAvailableSeatByTrain(@Param("trainId") String trainId);
}