package com.techelevator.model.jdbc;

import com.techelevator.DAOIntegrationTest;
import com.techelevator.model.Space;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class JDBCSpaceDAOTest extends DAOIntegrationTest {

    private static SingleConnectionDataSource dataSource;
    private JDBCSpaceDAO jdbcSpaceDAO;

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
    public void getSpaceByVenueId() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        jdbcSpaceDAO = new JDBCSpaceDAO(getDataSource());

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

        //Creates a dummy space object
        Space space = makeSpace(spaceId, "Test Space", true, "Apr", "Jul", new BigDecimal(100), 100);

        //Test method
        List<Space> testSpace = jdbcSpaceDAO.getSpaceByVenueId(venueId);

        //Assert both dummy values are equal
        assertSpacesAreEqual(space, testSpace.get(0));
    }

    @Test
    public void getSpaceById() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        jdbcSpaceDAO = new JDBCSpaceDAO(getDataSource());

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

        //Creates a dummy space object
        Space space = makeSpace(spaceId, "Test Space", true, "Apr", "Jul", new BigDecimal(100), 100);

        //Test method
        Space testSpace = jdbcSpaceDAO.getSpaceById(spaceId);

        //Assert both dummy values are equal
        assertSpacesAreEqual(space, testSpace);
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

    private Space mapRowToSpace(SqlRowSet results) {
        Space space = new Space();

        space.setSpaceId(results.getLong("id"));
        space.setSpaceName(results.getString("name"));
        space.setAccessible(results.getBoolean("is_accessible"));
        if (results.getInt("open_from") != 0) {
            space.setOpeningMonth(Month.of(Integer.parseInt(results.getString("open_from"))).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }
        if (results.getInt("open_to") != 0) {
            space.setOpeningMonth(Month.of(Integer.parseInt(results.getString("open_to"))).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }
        space.setDailyRate(results.getBigDecimal("daily_rate"));
        space.setMaxOccupancy(results.getInt("max_occupancy"));

        return space;
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

    protected DataSource getDataSource() {
        return dataSource;
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