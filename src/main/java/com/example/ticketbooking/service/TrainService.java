package com.example.ticketbooking.service;

import com.example.ticketbooking.Entities.Train;
import com.example.ticketbooking.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    @Cacheable(value = "trains", key = "#source + '-' + #destination")
    public List<Train> findTrainsByStations(String source, String destination) {
        List<Train> allTrains = trainRepository.findAll();
        return allTrains.stream()
                .filter(train -> train.getStations().contains(source) && train.getStations().contains(destination))
                .collect(Collectors.toList());
    }
}