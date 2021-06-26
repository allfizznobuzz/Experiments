package com.techelevator.model.jdbc;

import com.techelevator.model.Venue;
import com.techelevator.model.VenueDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JDBCVenueDAO implements VenueDAO {

    private JdbcTemplate jdbcTemplate;
    private JDBCSpaceDAO jdbcSpaceDAO;

    public JDBCVenueDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcSpaceDAO = new JDBCSpaceDAO(dataSource);
    }

    @Override
    public List<Venue> getListOfVenues() {
        List<Venue> listOfVenues = new ArrayList<>();

        String sqlSelectVenue = "SELECT venue.id AS venue_id, venue.name AS venue_name, venue.description AS venue_description, city.name AS city_name, state.abbreviation AS state_abbreviation " +
                                "FROM venue " +
                                "JOIN city ON venue.city_id = city.id " +
                                "JOIN state ON city.state_abbreviation = state.abbreviation " +
                                "ORDER BY venue.name";
        SqlRowSet templateResults = jdbcTemplate.queryForRowSet(sqlSelectVenue);

        while (templateResults.next()) {
            List<String> categoryList = new ArrayList<>();

            Venue newVenue = mapRowToVenue(templateResults);
            listOfVenues.add(newVenue);

            newVenue.setSpaceList(jdbcSpaceDAO.getSpaceByVenueId(newVenue.getVenueId()));

            String sqlSelectCategory = "SELECT category.name " +
                                       "FROM category " +
                                       "JOIN category_venue ON category_venue.category_id = category.id " +
                                       "JOIN venue ON venue.id = category_venue.venue_id " +
                                       "WHERE venue.id = ?";
            SqlRowSet categoryResults = jdbcTemplate.queryForRowSet(sqlSelectCategory, newVenue.getVenueId());

            while (categoryResults.next()) {
                categoryList.add(categoryResults.getString("name"));
            }

            newVenue.setCategoryList(categoryList);
        }

        return listOfVenues;
    }

    @Override
    public Venue getVenueById(long venueId) {
        Venue venue = null;
        List<String> categoryList = new ArrayList<>();

        String sqlSelectVenueById = "SELECT venue.id AS venue_id, venue.name AS venue_name, venue.description AS venue_description, city.name AS city_name, state.abbreviation AS state_abbreviation " +
                                    "FROM venue " +
                                    "JOIN city ON venue.city_id = city.id " +
                                    "JOIN state ON city.state_abbreviation = state.abbreviation " +
                                    "WHERE venue.id = ? " +
                                    "ORDER BY venue.name";
        SqlRowSet venueResults = jdbcTemplate.queryForRowSet(sqlSelectVenueById, venueId);

        if (venueResults.next()) {
            venue = mapRowToVenue(venueResults);
            venue.setSpaceList(jdbcSpaceDAO.getSpaceByVenueId(venue.getVenueId()));
        }

        String sqlSelectCategory = "SELECT category.name " +
                                   "FROM category " +
                                   "JOIN category_venue ON category_venue.category_id = category.id " +
                                   "JOIN venue ON venue.id = category_venue.venue_id " +
                                   "WHERE venue.id = ?";
        SqlRowSet categoryResults = jdbcTemplate.queryForRowSet(sqlSelectCategory, venueId);

        while (categoryResults.next()) {
            categoryList.add(categoryResults.getString("name"));
        }

        if(venue != null) {
            venue.setCategoryList(categoryList);
        }

        return venue;
    }

    private Venue mapRowToVenue(SqlRowSet results) {
        Venue venue = new Venue();
        venue.setVenueId(results.getLong("venue_id"));
        venue.setVenueName(results.getString("venue_name"));
        venue.setDescription(results.getString("venue_description"));
        venue.setCity(results.getString("city_name"));
        venue.setState(results.getString("state_abbreviation"));
        return venue;
    }

    //***********************************************************
    //WARNING - BELOW IS FOR NON-MVP PURPOSES - NO TESTS MADE YET
    //***********************************************************

    @Override
    public List<String> getAllCategories() {
        List<String> categoryList = new ArrayList<>();

        String sqlSelectCategory = "SELECT category.name " +
                                   "FROM category";
        SqlRowSet categoryResults = jdbcTemplate.queryForRowSet(sqlSelectCategory);

        while (categoryResults.next()) {
            categoryList.add(categoryResults.getString("name"));
        }

        return categoryList;
    }

 }
