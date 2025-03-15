package com.example.flightapp.controller;

import com.example.flightapp.model.Flight;
import com.example.flightapp.repository.FlightRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        return flight.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
