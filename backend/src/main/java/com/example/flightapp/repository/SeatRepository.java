package com.example.flightapp.repository;

import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlight(Flight flight);
    Optional<Seat> findBySeatNumberAndFlight_Id(String seatNumber, Long flightId); // ✅ Fix: Ensure seat belongs to correct flight

}