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

    // ✅ Seat selection endpoint
    @PostMapping("/{id}/select-seat")
    public ResponseEntity<String> selectSeat(@PathVariable Long id, @RequestBody SeatSelectionRequest request) {
        Optional<Seat> seatOpt = seatRepository.findById(request.getSeatId());

        if (seatOpt.isPresent()) {
            Seat seat = seatOpt.get();
            if (seat.isOccupied()) {  // ✅ Fix: Only book if the seat is available
                return ResponseEntity.badRequest().body("Seat is already booked!");
            }

            seat.setOccupied(true); // ✅ Set seat as occupied
            seatRepository.save(seat);
            return ResponseEntity.ok("✅ Seat booked successfully!");
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Request class for seat booking
    public static class SeatSelectionRequest {
        private Long seatId;
        public Long getSeatId() { return seatId; }
        public void setSeatId(Long seatId) { this.seatId = seatId; }
    }
}
