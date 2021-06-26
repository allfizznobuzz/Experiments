package com.techelevator.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReservationDAO {

    public List<Space> checkAvailability(long venueId, LocalDate startDate, LocalDate endDate, int numberOfGuests);

    public Reservation saveReservation(long spaceId, int numberOfGuests, LocalDate arrivalDate, LocalDate departureDate, String customerName, String lengthOfTrip);

    //***********************************************************
    //WARNING - BELOW IS FOR NON-MVP PURPOSES - NO TESTS MADE YET
    //***********************************************************

    public Map<Space, String> checkAvailabilityAdvancedSearch(LocalDate startDate, LocalDate endDate, int numberOfGuests, Boolean needsAccessible, BigDecimal budget, String category);

    public List<Space> checkAvailability(long venueId, LocalDate startDate, LocalDate endDate, int numberOfGuests, Boolean needsAccessible, BigDecimal budget);

    public List<Reservation> getAllReservationsInTheNextThirtyDays();
}
