package com.example.ticketbooking.config;

import com.example.ticketbooking.Entities.Seat;
import com.example.ticketbooking.Entities.Train;
import com.example.ticketbooking.repository.SeatRepository;
import com.example.ticketbooking.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only seed data if no trains exist to avoid duplication on restart
        if (trainRepository.count() == 0) {
            // Create Train 1
            Train expressTrain = new Train();
            expressTrain.setTrainId("EXP-001");
            expressTrain.setTrainNo("12015");
            expressTrain.setStations(List.of("Mumbai", "Pune", "Solapur", "Hyderabad"));
            trainRepository.save(expressTrain);
            createSeatsForTrain(expressTrain, 50);

            // Create Train 2
            Train shuttleTrain = new Train();
            shuttleTrain.setTrainId("SHT-002");
            shuttleTrain.setTrainNo("51318");
            shuttleTrain.setStations(List.of("Pune", "Lonavala", "Karjat", "Dadar"));
            trainRepository.save(shuttleTrain);
            createSeatsForTrain(shuttleTrain, 30);
        }
    }

    private void createSeatsForTrain(Train train, int numberOfSeats) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= numberOfSeats; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber("S" + i);
            seat.setBooked(false);
            seat.setTrain(train);
            seats.add(seat);
        }
        seatRepository.saveAll(seats);
    }
}
