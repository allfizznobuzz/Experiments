package com.techelevator.model.jdbc;

import com.techelevator.model.Reservation;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.Space;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCReservationDAO implements ReservationDAO {

    private JdbcTemplate jdbcTemplate;
    private JDBCSpaceDAO jdbcSpaceDAO;

    public JDBCReservationDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcSpaceDAO = new JDBCSpaceDAO(dataSource);
    }

    @Override
    public List<Space> checkAvailability(long venueId, LocalDate startDate, LocalDate endDate, int numberOfGuests) {
        List<Space> availableSpaces = new ArrayList<>();

        String first = startDate.toString();
        String last = endDate.toString();


        String sqlSelectAvailableSpaces = "SELECT space.venue_id, space.id, space.name, space.is_accessible, space.open_from, space.open_to, CAST(space.daily_rate AS decimal), space.max_occupancy " +
                                          "FROM space " +
                                          "JOIN venue ON venue.id = space.venue_id " +
                                          "WHERE venue.id = ? " +
                                          "AND (((open_from <= EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) AND EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) <= open_to) OR (open_from IS NULL and open_to IS NULL))) " +
                                          "AND max_occupancy >= ? " +
                                          "AND space.id NOT IN (SELECT space_id FROM reservation " +
                                          "WHERE (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                                          "OR (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                                          "OR (reservation.start_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)) " +
                                          "OR (reservation.end_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
                                          "AND CAST(space.daily_rate AS decimal) <= ? " +
                                          "AND space.is_accessible = TRUE " +
                                          "GROUP BY space.id " +
                                          "ORDER BY space.daily_rate ASC " +
                                          "LIMIT 5";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAvailableSpaces, venueId, first, last, numberOfGuests, first, last, first, last, first, last);

        while (results.next()) {
            Space newSpace = jdbcSpaceDAO.mapRowToSpace(results);
            availableSpaces.add(newSpace);
        }

        return availableSpaces;

    }


    @Override
    public Reservation saveReservation(long spaceId, int numberOfGuests, LocalDate arrivalDate, LocalDate departureDate, String customerName, String lengthOfTrip) {
        long reservationId = getNextReservationId();
        Reservation reservation = new Reservation();

        String first = arrivalDate.toString();
        String last = departureDate.toString();

        String sqlSaveReservation = "INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for)\n" +
                                    "VALUES (?, ?, ?, CAST(? AS DATE), CAST(? AS DATE), ?)";
        jdbcTemplate.update(sqlSaveReservation, reservationId, spaceId, numberOfGuests, first, last, customerName);

        String sqlReturnReservation = "SELECT reservation_id, space_id, space.name AS space_name, venue.name AS venue_name, number_of_attendees, start_date, end_date, reserved_for " +
                                      "FROM reservation " +
                                      "JOIN space ON space.id = reservation.space_id " +
                                      "JOIN venue ON venue.id = space.venue_id " +
                                      "WHERE reservation_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlReturnReservation, reservationId);

        if(result.next()) {
            reservation = mapRowToReservation(result, lengthOfTrip);
        }

        return reservation;
    }

    private Reservation mapRowToReservation(SqlRowSet result, String lengthOfTrip) {
        Reservation newReservation = new Reservation();


        newReservation.setReservationId(result.getLong("reservation_id"));
        newReservation.setSpaceId(result.getLong("space_id"));
        newReservation.setVenueName(result.getString("venue_name"));
        newReservation.setSpaceName(result.getString("space_name"));
        newReservation.setCustomerName(result.getString("reserved_for"));
        newReservation.setNumberOfGuests(result.getInt("number_of_attendees"));
        newReservation.setArrivalDate(result.getDate("start_date").toLocalDate());
        newReservation.setDepartureDate(result.getDate("end_date").toLocalDate());

        BigDecimal dailyRate = jdbcSpaceDAO.getSpaceById(newReservation.getSpaceId()).getDailyRate();

        newReservation.setTotalCost(dailyRate.multiply(new BigDecimal((lengthOfTrip))));

        return newReservation;
    }

    private Reservation mapRowToReservation(SqlRowSet result) {
        Reservation newReservation = new Reservation();

        newReservation.setVenueName(result.getString("venue_name"));
        newReservation.setSpaceName(result.getString("space_name"));
        newReservation.setCustomerName(result.getString("reserved_for"));
        newReservation.setArrivalDate(result.getDate("start_date").toLocalDate());
        newReservation.setDepartureDate(result.getDate("end_date").toLocalDate());

        return newReservation;
    }

    private long getNextReservationId() {
        SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");

        if (nextIdResult.next()) {
            return nextIdResult.getInt(1);
        } else {
            throw new RuntimeException("Something went wrong while getting an id for the new reservation.");
        }
    }

    //***********************************************************
    //WARNING - BELOW IS FOR NON-MVP PURPOSES - NO TESTS MADE YET
    //***********************************************************

    @Override
    public List<Reservation> getAllReservationsInTheNextThirtyDays() {
        List<Reservation> listOfReservations = new ArrayList<>();

        String sqlReturnReservations = "SELECT space.name AS space_name, venue.name AS venue_name, start_date, end_date, reserved_for " +
                "FROM reservation " +
                "JOIN space ON space.id = reservation.space_id " +
                "JOIN venue ON venue.id = space.venue_id " +
                "WHERE start_date BETWEEN NOW() AND NOW() + '31 DAY'";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlReturnReservations);

        while(result.next()) {
            Reservation reservation = mapRowToReservation(result);
            listOfReservations.add(reservation);
        }

        return listOfReservations;
    }

    @Override
    public List<Space> checkAvailability(long venueId, LocalDate startDate, LocalDate endDate, int numberOfGuests, Boolean needsAccessible, BigDecimal budget) {
        List<Space> availableSpaces = new ArrayList<>();

        String first = startDate.toString();
        String last = endDate.toString();

        if(needsAccessible) {

            String sqlSelectAvailableSpaces = "SELECT space.venue_id, space.id, space.name, space.is_accessible, space.open_from, space.open_to, CAST(space.daily_rate AS decimal), space.max_occupancy " +
                    "FROM space " +
                    "JOIN venue ON venue.id = space.venue_id " +
                    "WHERE venue.id = ? " +
                    "AND (((open_from <= EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) AND EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) <= open_to) OR (open_from IS NULL and open_to IS NULL))) " +
                    "AND max_occupancy >= ? " +
                    "AND space.id NOT IN (SELECT space_id FROM reservation " +
                    "WHERE (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                    "OR (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                    "OR (reservation.start_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)) " +
                    "OR (reservation.end_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
                    "AND CAST(space.daily_rate AS decimal) <= ? " +
                    "AND space.is_accessible = TRUE " +
                    "GROUP BY space.id " +
                    "ORDER BY space.daily_rate ASC " +
                    "LIMIT 5";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAvailableSpaces, venueId, first, last, numberOfGuests, first, last, first, last, first, last, budget);

            while (results.next()) {
                Space newSpace = jdbcSpaceDAO.mapRowToSpace(results);
                availableSpaces.add(newSpace);
            }

            return availableSpaces;

        }

        String sqlSelectAvailableSpaces = "SELECT space.venue_id, space.id, space.name, space.is_accessible, space.open_from, space.open_to, CAST(space.daily_rate AS decimal), space.max_occupancy " +
                "FROM space " +
                "JOIN venue ON venue.id = space.venue_id " +
                "WHERE venue.id = ? " +
                "AND (((open_from <= EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) AND EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) <= open_to) OR (open_from IS NULL and open_to IS NULL))) " +
                "AND max_occupancy >= ? " +
                "AND space.id NOT IN (SELECT space_id FROM reservation " +
                "WHERE (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                "OR (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                "OR (reservation.start_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)) " +
                "OR (reservation.end_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
                "AND CAST(space.daily_rate AS decimal) <= ? " +
                "GROUP BY space.id " +
                "ORDER BY space.daily_rate ASC " +
                "LIMIT 5";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAvailableSpaces, venueId, first, last, numberOfGuests, first, last, first, last, first, last, budget);

        while (results.next()) {
            Space newSpace = jdbcSpaceDAO.mapRowToSpace(results);
            availableSpaces.add(newSpace);
        }

        return availableSpaces;

    }

    public Map<Space, String>  checkAvailabilityAdvancedSearch(LocalDate startDate, LocalDate endDate, int numberOfGuests, Boolean needsAccessible, BigDecimal budget, String category) {
        Map<Space, String>  availableSpaces = new HashMap<>();
        SqlRowSet results = null;

        String first = startDate.toString();
        String last = endDate.toString();

        if(needsAccessible && category.equalsIgnoreCase("N")) {

            String sqlSelectAvailableSpaces = "SELECT venue.name AS venue_name, space.id, space.name, space.is_accessible, space.open_from, space.open_to, CAST(space.daily_rate AS decimal), space.max_occupancy " +
                    "FROM space " +
                    "JOIN venue ON venue.id = space.venue_id " +
                    "WHERE (((open_from <= EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) AND EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) <= open_to) OR (open_from IS NULL and open_to IS NULL))) " +
                    "AND max_occupancy >= ? " +
                    "AND space.id NOT IN (SELECT space_id FROM reservation " +
                    "WHERE (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                    "OR (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                    "OR (reservation.start_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)) " +
                    "OR (reservation.end_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
                    "AND CAST(space.daily_rate AS decimal) <= ? " +
                    "AND space.is_accessible = TRUE " +
                    "GROUP BY space.id, venue.name " +
                    "ORDER BY space.daily_rate ASC ";
            results = jdbcTemplate.queryForRowSet(sqlSelectAvailableSpaces, first, last, numberOfGuests, first, last, first, last, first, last, budget);

        } else if (needsAccessible) {

            String sqlSelectAvailableSpaces = "SELECT venue.name AS venue_name, space.id, space.name, space.is_accessible, space.open_from, space.open_to, CAST(space.daily_rate AS decimal), space.max_occupancy " +
                    "FROM space " +
                    "JOIN venue ON venue.id = space.venue_id " +
                    "JOIN category_venue ON category_venue.venue_id = venue.id " +
                    "JOIN category ON category.id = category_venue.category_id " +
                    "WHERE (((open_from <= EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) AND EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) <= open_to) OR (open_from IS NULL and open_to IS NULL))) " +
                    "AND max_occupancy >= ? " +
                    "AND space.id NOT IN (SELECT space_id FROM reservation " +
                    "WHERE (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                    "OR (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                    "OR (reservation.start_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)) " +
                    "OR (reservation.end_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
                    "AND CAST(space.daily_rate AS decimal) <= ? " +
                    "AND space.is_accessible = TRUE " +
                    "AND category.name = ? " +
                    "GROUP BY space.id, venue.name " +
                    "ORDER BY space.daily_rate ASC";

            results = jdbcTemplate.queryForRowSet(sqlSelectAvailableSpaces, first, last, numberOfGuests, first, last, first, last, first, last, budget, category);
        } else if (category.equalsIgnoreCase("N")) {

            String sqlSelectAvailableSpaces = "SELECT venue.name AS venue_name, space.id, space.name, space.is_accessible, space.open_from, space.open_to, CAST(space.daily_rate AS decimal), space.max_occupancy " +
                    "FROM space " +
                    "JOIN venue ON venue.id = space.venue_id " +
                    "WHERE (((open_from <= EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) AND EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) <= open_to) OR (open_from IS NULL and open_to IS NULL))) " +
                    "AND max_occupancy >= ? " +
                    "AND space.id NOT IN (SELECT space_id FROM reservation " +
                    "WHERE (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                    "OR (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                    "OR (reservation.start_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)) " +
                    "OR (reservation.end_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
                    "AND CAST(space.daily_rate AS decimal) <= ? " +
                    "GROUP BY space.id, venue.name " +
                    "ORDER BY space.daily_rate ASC";

            results = jdbcTemplate.queryForRowSet(sqlSelectAvailableSpaces, first, last, numberOfGuests, first, last, first, last, first, last, budget);
        } else {

            String sqlSelectAvailableSpaces = "SELECT venue.name AS venue_name, space.id, space.name, space.is_accessible, space.open_from, space.open_to, CAST(space.daily_rate AS decimal), space.max_occupancy " +
                    "FROM space " +
                    "JOIN venue ON venue.id = space.venue_id " +
                    "JOIN category_venue ON category_venue.venue_id = venue.id " +
                    "JOIN category ON category.id = category_venue.category_id " +
                    "WHERE (((open_from <= EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) AND EXTRACT(MONTH FROM to_date(?, 'YYYY-MM-DD')) <= open_to) OR (open_from IS NULL and open_to IS NULL))) " +
                    "AND max_occupancy >= ? " +
                    "AND space.id NOT IN (SELECT space_id FROM reservation " +
                    "WHERE (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                    "OR (CAST(? AS DATE) BETWEEN reservation.start_date AND reservation.end_date) " +
                    "OR (reservation.start_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)) " +
                    "OR (reservation.end_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))) " +
                    "AND CAST(space.daily_rate AS decimal) <= ? " +
                    "AND category.name = ? " +
                    "GROUP BY space.id, venue.name " +
                    "ORDER BY space.daily_rate ASC";

            results = jdbcTemplate.queryForRowSet(sqlSelectAvailableSpaces, first, last, numberOfGuests, first, last, first, last, first, last, budget, category);
        }

        while (results.next()) {
            Space newSpace = jdbcSpaceDAO.mapRowToSpace(results);
            availableSpaces.put(newSpace, results.getString("venue_name"));
        }

        return availableSpaces;

    }
}
