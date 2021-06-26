package com.techelevator.model.jdbc;

import com.techelevator.model.Reservation;
import com.techelevator.model.Space;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JDBCReservationDAOTest {

    private static SingleConnectionDataSource dataSource;
    private JDBCReservationDAO jdbcReservationDAO;

    @BeforeClass
    public static void setupDataSource() {
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior_venues");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        /*
         * The following line disables autocommit for connections returned by this
         * DataSource. This allows us to rollback any changes after each test
         */
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


    protected DataSource getDataSource() {
        return dataSource;
    }

    @Test
    public void checkAvailability() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcReservationDAO = new JDBCReservationDAO(dataSource);

        List<Space> listOfSpaces = new ArrayList<>();

        long venueId = getNextVenueId();
        long spaceId = getNextSpaceId();
        long firstReservationId = getNextReservationId();
        long secondReservationId = getNextReservationId();
        long thirdReservationId = getNextReservationId();

        //Creates a dummy venue in postgres
        String sqlInsertVenue = "INSERT INTO venue (id, name, city_id, description) " +
                                "VALUES (?, 'Test Venue', ?, 'This is our test venue.')";
        jdbcTemplate.update(sqlInsertVenue, venueId, 1);

        //Creates a dummy space in postgres
        String sqlInsertSpace = "INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) " +
                                "VALUES (?, ?, 'Test Space', true, 4, 6, CAST(100 AS MONEY), 500)";
        jdbcTemplate.update(sqlInsertSpace, spaceId, venueId);

        //Creates a dummy reservation in postgres
        String sqlInsertFirstReservation = "INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) " +
                                           "VALUES (?, ?, 0, '6-10-2021', '6-27-2021', 'Test')";
        jdbcTemplate.update(sqlInsertFirstReservation, firstReservationId, spaceId);

        LocalDate first = LocalDate.of(2021, 4, 1);
        LocalDate second = LocalDate.of(2021, 5, 10);

        listOfSpaces = jdbcReservationDAO.checkAvailability(venueId, first, second, 499, false, new BigDecimal(6000));

        assertEquals(1, listOfSpaces.size());

        Space space = makeSpace(spaceId, "Test Space", true, "Apr", "Jul", new BigDecimal(100), 500);

        assertSpacesAreEqual(space, listOfSpaces.get(0));

    }

    @Test
    public void saveReservation() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcReservationDAO = new JDBCReservationDAO(dataSource);

        //Create dummy IDs
        long venueId = getNextVenueId();
        long spaceId = getNextSpaceId();

        //Creates a dummy venue in postgres
        String sqlInsertVenue = "INSERT INTO venue (id, name, city_id, description) " +
                                "VALUES (?, 'Test Venue', ?, 'This is our test venue.')";
        jdbcTemplate.update(sqlInsertVenue, venueId, 1);

        //Creates a dummy space in postgres
        String sqlInsertSpace = "INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) " +
                                "VALUES (?, ?, 'Test Space', true, 4, 6, CAST(100 AS MONEY), 100)";
        jdbcTemplate.update(sqlInsertSpace, spaceId, venueId);

        LocalDate firstDay = LocalDate.of(2021, 6, 7);
        LocalDate lastDay = LocalDate.of(2021, 6, 27);
        Reservation reservation = getReservation(spaceId, 100, firstDay, lastDay, "Test");

        Reservation newReservation = jdbcReservationDAO.saveReservation(reservation.getSpaceId(), reservation.getNumberOfGuests(), reservation.getArrivalDate(), reservation.getDepartureDate(), reservation.getCustomerName(), "10");

        reservation.setReservationId(newReservation.getReservationId());

        String sqlSelectReservation = "SELECT *" +
                                      "FROM reservation " +
                                      "WHERE reservation_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectReservation, newReservation.getReservationId());

        Reservation testReservation = new Reservation();
        if (results.next()) {
            testReservation = mapRowToReservation(results);
        }

        assertReservationsAreEqual(reservation, testReservation);

    }


    private long getNextSpaceId() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('space_id_seq')");

        if (nextIdResult.next()) {
            return nextIdResult.getInt(1);
        } else {
            throw new RuntimeException("Something went wrong while getting an id for the new space.");
        }
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

    private long getNextReservationId() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        jdbcReservationDAO = new JDBCReservationDAO(getDataSource());

        SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");

        if (nextIdResult.next()) {
            return nextIdResult.getInt(1);
        } else {
            throw new RuntimeException("Something went wrong while getting an id for the new reservation.");
        }
    }

    private Reservation getReservation(long spaceId, int numberOfAttendees, LocalDate arrivalDate, LocalDate departureDate, String reservedFor) {
        Reservation reservation = new Reservation();
        reservation.setSpaceId(spaceId);
        reservation.setNumberOfGuests(numberOfAttendees);
        reservation.setArrivalDate(arrivalDate);
        reservation.setDepartureDate(departureDate);
        reservation.setCustomerName(reservedFor);
        return reservation;
    }

    private Reservation mapRowToReservation(SqlRowSet results) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(results.getLong("reservation_id"));
        reservation.setSpaceId(results.getLong("space_id"));
        reservation.setNumberOfGuests(results.getInt("number_of_attendees"));
        reservation.setArrivalDate(results.getDate("start_date").toLocalDate());
        reservation.setDepartureDate(results.getDate("end_date").toLocalDate());
        reservation.setCustomerName(results.getString("reserved_for"));
        return reservation;
    }

    private void assertReservationsAreEqual(Reservation expected, Reservation actual) {
        assertEquals(expected.getReservationId(), actual.getReservationId());
        assertEquals(expected.getSpaceId(), actual.getSpaceId());
        assertEquals(expected.getNumberOfGuests(), actual.getNumberOfGuests());
        assertEquals(expected.getArrivalDate(), actual.getArrivalDate());
        assertEquals(expected.getDepartureDate(), actual.getDepartureDate());
        assertEquals(expected.getCustomerName(), actual.getCustomerName());
    }

    private Space makeSpace(long id, String name, boolean isAccessible, String openingMonth, String closingMonth, BigDecimal rate, int maxOccupancy) {
        Space space = new Space();

        space.setSpaceId(id);
        space.setSpaceName(name);
        space.setAccessible(isAccessible);
        space.setOpeningMonth(openingMonth);
        space.setClosingMonth(closingMonth);
        space.setDailyRate(rate);
        space.setMaxOccupancy(maxOccupancy);

        return space;
    }

    private void assertSpacesAreEqual(Space expected, Space actual) {
        assertEquals(expected.getSpaceId(), actual.getSpaceId());
        assertEquals(expected.getSpaceName(), actual.getSpaceName());
        assertEquals(expected.isAccessible(), actual.isAccessible());
        assertEquals(expected.getOpeningMonth(), actual.getOpeningMonth());
        assertEquals(expected.getClosingMonth(), expected.getClosingMonth());
        assertEquals(expected.getMaxOccupancy(), actual.getMaxOccupancy());
        assertEquals(expected.getDailyRate(), expected.getDailyRate());
    }
}