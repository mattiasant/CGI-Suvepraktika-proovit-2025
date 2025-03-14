package com.example.flightapp.controller;


import com.example.flightapp.model.Seat;
import com.example.flightapp.repository.FlightRepository;
import com.example.flightapp.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;
    @Autowired
    private FlightRepository flightRepository;

    @GetMapping
    public List<Seat> getAll() {
        return seatService.getAllSeats(flightRepository.findAll().get(1));
    }

}