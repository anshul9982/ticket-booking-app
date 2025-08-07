package com.example.ticketbooking.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketbooking.Entities.Train;
import com.example.ticketbooking.service.TrainService;

@RestController
@RequestMapping("/trains")
public class TrainController {

    @Autowired
    TrainService trainService;
    @GetMapping("/search-trains")
    public ResponseEntity<?> getTrainsByStations(@RequestParam String source, @RequestParam String destination) {
        List<Train> trains = trainService.findTrainsByStations(source, destination);
        if (trains != null) {
            return new ResponseEntity<>("Trains are: " + trains, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No trains found", HttpStatus.NOT_FOUND);
            
        }
    }
}
