package com.example.flightapp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination;
    private LocalDate date;
    private LocalTime flightTime;
    private Double price;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats = new ArrayList<>();

    // Constructor without ID for saving new flights
    public Flight(String destination, LocalDate date, LocalTime flightTime, Double price, List<Seat> seats) {
        this.destination = destination;
        this.date = date;
        this.flightTime = flightTime;
        this.price = price;
        this.seats = seats;
    }

    public Flight() {

    }

    public Long getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getFlightTime() {
        return flightTime;
    }

    public Double getPrice() {
        return price;
    }
}