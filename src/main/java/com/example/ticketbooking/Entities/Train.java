package com.example.ticketbooking.Entities;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Train {
    @Id
    private String trainId;
    private String trainNo;

    @OneToMany(mappedBy = "train")
    private List<Seat> seats;

    @ElementCollection
    private Map<String, Time> stationTime;

    @ElementCollection
    private List<String> stations;
}
