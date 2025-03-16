package com.example.flightapp.controller;

import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Seat;
import com.example.flightapp.repository.FlightRepository;
import com.example.flightapp.repository.SeatRepository;
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

    @Autowired
    private SeatRepository seatRepository; // ✅ Added SeatRepository

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        if (flights.isEmpty()) {
            return ResponseEntity.noContent().build(); // ✅ Return 204 if no flights
        }
        return ResponseEntity.ok(flights);
    }

    // ✅ Fetch a flight by ID
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        System.out.println("🛠️ Fetching flight with ID: " + id);
        return flightRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    System.out.println("❌ Flight not found: ID " + id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping("/{id}/select-seat")
    public ResponseEntity<String> selectSeat(@PathVariable Long id, @RequestBody SeatSelectionRequest request) {
        if (request.getSeatId() == null || request.getSeatId().isEmpty()) {
            return ResponseEntity.badRequest().body("Seat ID is missing!");
        }

        Optional<Flight> flightOpt = flightRepository.findById(id);
        if (flightOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Seat> seatOpt = seatRepository.findBySeatNumberAndFlight_Id(request.getSeatId(), id);

        if (seatOpt.isPresent()) {
            Seat seat = seatOpt.get();
            if (seat.isOccupied()) {
                return ResponseEntity.badRequest().body("Seat is already booked!");
            }

            seat.setOccupied(true);
            seatRepository.save(seat);
            return ResponseEntity.ok("Seat booked successfully!");
        }
        return ResponseEntity.notFound().build();
    }

    public static class SeatSelectionRequest {
        private String seatId;

        public String getSeatId() {
            return seatId;
        }

        public void setSeatId(String seatId) {
            this.seatId = seatId;
        }
    }
}
