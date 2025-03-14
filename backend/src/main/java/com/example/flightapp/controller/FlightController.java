package com.example.flightapp.controller;

import com.example.flightapp.model.Flight;
import com.example.flightapp.repository.FlightRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping
    public List<Flight> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        System.out.println("Flights fetched: " + flights); // Debugging
        return flights;
    }
}
