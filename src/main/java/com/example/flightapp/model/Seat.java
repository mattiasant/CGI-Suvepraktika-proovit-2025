package com.example.flightapp.model;

import jakarta.persistence.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;
    private boolean isOccupied;
    private boolean isWindow;
    private boolean hasExtraLegroom;
    private boolean isNearExit;
    private boolean isFirstClass;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isWindow() {
        return isWindow;
    }

    public void setWindow(boolean window) {
        isWindow = window;
    }

    public boolean hasExtraLegroom() {
        return hasExtraLegroom;
    }

    public void setHasExtraLegroom(boolean hasExtraLegroom) {
        this.hasExtraLegroom = hasExtraLegroom;
    }

    public boolean isNearExit() {
        return isNearExit;
    }

    public void setNearExit(boolean nearExit) {
        isNearExit = nearExit;
    }

    public boolean isFirstClass() {
        return isFirstClass;
    }

    public void setFirstClass(boolean firstClass) {
        isFirstClass = true;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}