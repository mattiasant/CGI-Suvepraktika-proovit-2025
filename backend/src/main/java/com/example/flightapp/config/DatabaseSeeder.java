package com.example.flightapp.config;

import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Seat;
import com.example.flightapp.repository.FlightRepository;
import com.example.flightapp.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final Random random = new Random(); // To randomize seat attributes

    @Autowired
    public DatabaseSeeder(FlightRepository flightRepository, SeatRepository seatRepository) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void run(String... args) {
        List<Flight> flights;

        if (flightRepository.count() == 0) {
            flights = List.of(
                    new Flight("RIGA RIX", LocalDate.of(2025, 10, 6), LocalTime.of(3, 12), 124.00, new ArrayList<>()),
                    new Flight("VANTAA HEL", LocalDate.of(2025, 6, 3), LocalTime.of(2, 30), 256.00, new ArrayList<>()),
                    new Flight("GATWICK LGW", LocalDate.of(2025, 4, 2), LocalTime.of(5, 12), 532.00, new ArrayList<>()),
                    new Flight("MALTA MLA", LocalDate.of(2026, 12, 8), LocalTime.of(6, 43), 6874.00, new ArrayList<>()),
                    new Flight("LOS ANGELES LAX", LocalDate.of(2025, 12, 12), LocalTime.of(17, 12), 1524.00, new ArrayList<>()),
                    new Flight("NEW YORK JFK", LocalDate.of(2025, 8, 1), LocalTime.of(15, 0), 1888.00, new ArrayList<>()),
                    new Flight("MELBOURNE MEB", LocalDate.of(2025, 6, 11), LocalTime.of(12, 1), 665.00, new ArrayList<>()),
                    new Flight("TOKYO HND", LocalDate.of(2025, 10, 6), LocalTime.of(14, 0), 882.00, new ArrayList<>()),
                    new Flight("DUBLIN DUB", LocalDate.of(2025, 7, 2), LocalTime.of(5, 16), 334.00, new ArrayList<>()),
                    new Flight("REYKJAVIK KEF", LocalDate.of(2026, 4, 7), LocalTime.of(7, 44), 87.00, new ArrayList<>())
            );

            // ✅ Save flights first
            flightRepository.saveAll(flights);
            System.out.println("✅ Flights added.");
        } else {
            flights = flightRepository.findAll(); // Retrieve existing flights to add seats
            System.out.println("✅ Flights already exist. Skipping flight creation.");
        }

        if (seatRepository.count() == 0) {
            List<Seat> seats = new ArrayList<>();
            int rowsPerFlight = 10; // Fixed number of rows per flight
            int seatsPerRow = 6;    // Fixed seats per row (A-F)

            for (Flight flight : flights) {
                for (int row = 1; row <= rowsPerFlight; row++) { // 10 rows per flight
                    for (char seatLetter = 'A'; seatLetter < 'A' + seatsPerRow; seatLetter++) { // A-F seats
                        String seatNumber = row + String.valueOf(seatLetter);
                        boolean isOccupied = random.nextBoolean(); // Randomly set as occupied or available
                        boolean isWindow = (seatLetter == 'A' || seatLetter == 'F'); // Window seats at both ends
                        boolean hasExtraLegroom = row == 1 || row == 10; // First and last row have extra legroom
                        boolean isNearExit = row == 1 || row == 2; // First two rows are near exit
                        boolean isFirstClass = row <= 2; // First two rows are first class

                        seats.add(new Seat(null, seatNumber, isOccupied, isWindow, hasExtraLegroom, isNearExit, isFirstClass, flight));
                    }
                }
            }

            // ✅ Save seats
            seatRepository.saveAll(seats);
            System.out.println("✅ Seats added in a consistent grid (10 rows x 6 seats per row).");
        } else {
            System.out.println("✅ Seats already exist. Skipping seat creation.");
        }
    }
}
