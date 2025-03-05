package com.example.flightapp.repository;

import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> getAllSeats(Flight flight);
}