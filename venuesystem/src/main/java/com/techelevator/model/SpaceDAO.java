package com.techelevator.model;

import java.util.List;

public interface SpaceDAO {

    public List<Space> getSpaceByVenueId(long venueId);

    public Space getSpaceById(long spaceId);
}
