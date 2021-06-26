package com.techelevator.model;

import java.util.List;

public interface VenueDAO {

    public List<Venue> getListOfVenues();

    public Venue getVenueById(long venueId);

    //***********************************************************
    //WARNING - BELOW IS FOR NON-MVP PURPOSES - NO TESTS MADE YET
    //***********************************************************

    public List<String> getAllCategories();
}
