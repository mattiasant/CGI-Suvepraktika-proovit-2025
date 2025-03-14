package com.example.flightapp.config;

import com.example.flightapp.model.Flight;
import com.example.flightapp.model.Seat;
import com.example.flightapp.repository.FlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final FlightRepository flightRepository;

    public DatabaseSeeder(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public void run(String... args) {
        List<Flight> flights = null;
        if (flightRepository.count() == 0) {
            flights = List.of(
                    new Flight( "RIGA RIX", LocalDate.of(2025, 10, 6), LocalTime.of(3, 12), 124.00, new ArrayList<Seat>()),
                    new Flight("VANTAA HEL", LocalDate.of(2025, 6, 3), LocalTime.of(2, 30), 256.00, new ArrayList<Seat>()),
                    new Flight("GATWICK LGW", LocalDate.of(2025, 4, 2), LocalTime.of(5, 12), 532.00, new ArrayList<Seat>()),
                    new Flight("MALTA MLA", LocalDate.of(2026, 12, 8), LocalTime.of(6, 43), 6874.00, new ArrayList<Seat>()),
                    new Flight("LOS ANGELES LAX", LocalDate.of(2025, 12, 12), LocalTime.of(17, 12), 1524.00, new ArrayList<Seat>()),
                    new Flight("JOHN F KENNEDY JFK", LocalDate.of(2025, 8, 1), LocalTime.of(15, 0), 1888.00, new ArrayList<Seat>()),
                    new Flight("MELBOURNE MEB", LocalDate.of(2025, 6, 11), LocalTime.of(12, 1), 665.00, new ArrayList<Seat>()),
                    new Flight("TOKYO HND", LocalDate.of(2025, 10, 6), LocalTime.of(14, 0), 882.00, new ArrayList<Seat>()),
                    new Flight("DUBLIN DUB", LocalDate.of(2025, 7, 2), LocalTime.of(5, 16), 334.00, new ArrayList<Seat>()),
                    new Flight("REYKJAVIK KEF", LocalDate.of(2026, 4, 7), LocalTime.of(7, 44), 87.00, new ArrayList<Seat>())
            );

            try {
                flightRepository.saveAll(flights);
                System.out.println("✅ Sample flights added to the database.");
            } catch (Exception e) {
                System.out.println("❌ Error while saving flights: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("✅ Database already contains flight data. Skipping seeding.");
        }
    }
}
