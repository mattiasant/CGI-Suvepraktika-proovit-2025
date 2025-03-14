package com.example.flightapp.service;

import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Seat;
import com.example.flightapp.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getAllSeats(Flight flight) {
        List<Seat> allSeats = seatRepository.findByFlight(flight);

        return allSeats;
    }
}