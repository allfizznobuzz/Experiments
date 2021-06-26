package com.techelevator.model.jdbc;

import com.techelevator.model.Venue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JDBCVenueDAOTest {

    private static SingleConnectionDataSource dataSource;
    private JDBCVenueDAO jdbcVenueDAO;

    @BeforeClass
    public static void setupDataSource() {
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior_venues");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setAutoCommit(false);
    }

    @AfterClass
    public static void closeDataSource() throws SQLException {
        dataSource.destroy();
    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }

    @Test
    public void getListOfVenues() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        jdbcVenueDAO = new JDBCVenueDAO(getDataSource());

        List<Venue> venueListBefore = new ArrayList<>();

        //Postgres query to retrieve all venues
        String sqlSelectVenues = "SELECT venue.id AS venue_id, venue.name AS venue_name, venue.description AS venue_description, city.name AS city_name, state.abbreviation AS state_abbreviation " +
                                 "FROM venue " +
                                 "JOIN city ON venue.city_id = city.id " +
                                 "JOIN state ON city.state_abbreviation = state.abbreviation " +
                                 "ORDER BY venue.name";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectVenues);

        //This will store the current amount of venues in postgres to a list
        while (results.next()) {
            Venue venue = mapRowToVenue(results);
            venueListBefore.add(venue);
        }

        //Creates first dummy venue in postgres
        String sqlInsertFirstVenue = "INSERT INTO venue (name, city_id, description) " +
                                     "VALUES ('First Test Venue', 1, 'This is our first test venue')";
        jdbcTemplate.update(sqlInsertFirstVenue);

        //Creates second dummy venue in postgres
        String sqlInsertSecondVenue = "INSERT INTO venue (name, city_id, description) " +
                                      "VALUES ('Second Test Venue', 1, 'This is our second test venue')";
        jdbcTemplate.update(sqlInsertSecondVenue);

        //Tests method
        List<Venue> newList = jdbcVenueDAO.getListOfVenues();

        //Asserts that the two dummy venues were added to the list.
        //This shows that the method was able to retrieve all venues from postgres
        assertEquals(newList.size(), venueListBefore.size() + 2);
    }

    @Test
    public void getVenueById() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        jdbcVenueDAO = new JDBCVenueDAO(getDataSource());

        //Create dummy ID
        long venueId = getNextVenueId();

        //Creates a dummy venue in postgres
        String sqlInsertVenue = "INSERT INTO venue (id, name, city_id, description) " +
                                "VALUES (?, 'Test Venue', 1, 'This is our test venue')";
        jdbcTemplate.update(sqlInsertVenue, venueId);

        //Creates a dummy venue object
        Venue venue = makeVenue(venueId, "Test Venue", "This is our test venue");

        //Assigns city value to dummy venue object
        String sqlSelectCityAndState = "SELECT city.name, state.abbreviation " +
                                       "FROM city JOIN state ON state.abbreviation = city.state_abbreviation " +
                                       "WHERE city.id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectCityAndState, 1);

        while(results.next()) {
            venue.setCity(results.getString("name"));
            venue.setState(results.getString("abbreviation"));
        }

        //Test method
        Venue testVenue = jdbcVenueDAO.getVenueById(venueId);

        //Assert both dummy values are equal
        assertVenuesAreEqual(venue, testVenue);
    }

    private Venue makeVenue(long id, String name, String description) {
        Venue newVenue = new Venue();

        newVenue.setVenueId(id);
        newVenue.setVenueName(name);
        newVenue.setDescription(description);

        return newVenue;
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

    private long getNextVenueId() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('venue_id_seq')");

        if (nextIdResult.next()) {
            return nextIdResult.getInt(1);
        } else {
            throw new RuntimeException("Something went wrong while getting an id for the new venue.");
        }
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

    private void assertVenuesAreEqual(Venue expected, Venue actual) {
        assertEquals(expected.getVenueId(), actual.getVenueId());
        assertEquals(expected.getVenueName(), actual.getVenueName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getState(), actual.getState());
    }
}