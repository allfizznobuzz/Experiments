package com.techelevator.model.jdbc;

import com.techelevator.model.Space;
import com.techelevator.model.SpaceDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JDBCSpaceDAO implements SpaceDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCSpaceDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Space> getSpaceByVenueId(long venueId) {
        List<Space> listOfSpaces = new ArrayList<>();

        String sqlSelectSpaceByVenueId = "SELECT space.id, space.name, space.is_accessible, space.open_from, space.open_to, CAST (daily_rate AS decimal), space.max_occupancy " +
                                         "FROM space " +
                                         "JOIN venue ON venue.id = space.venue_id " +
                                         "WHERE venue.id = ?" +
                                         "ORDER BY space.name";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectSpaceByVenueId, venueId);

        while(results.next()) {
            Space newSpace = mapRowToSpace(results);
            listOfSpaces.add(newSpace);
        }

        return listOfSpaces;
    }

    @Override
    public Space getSpaceById(long spaceId) {
        Space newSpace = null;

        String sqlSelectSpaceById= "SELECT id, name, is_accessible, open_from, open_to, CAST (daily_rate AS decimal), max_occupancy " +
                                   "FROM space " +
                                   "WHERE space.id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectSpaceById, spaceId);

        while(results.next()) {
            newSpace = mapRowToSpace(results);
        }

        return newSpace;
    }

    public Space mapRowToSpace(SqlRowSet results) {
        Space newSpace = new Space();

        newSpace.setSpaceId(results.getLong("id"));
        newSpace.setSpaceName(results.getString("name"));
        newSpace.setAccessible(results.getBoolean("is_accessible"));
        if (results.getInt("open_from") != 0) {
            newSpace.setOpeningMonth(Month.of(Integer.parseInt(results.getString("open_from"))).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }
        if (results.getInt("open_to") != 0) {
            newSpace.setClosingMonth(Month.of(Integer.parseInt(results.getString("open_to"))).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }
        newSpace.setDailyRate(results.getBigDecimal("daily_rate"));
        newSpace.setMaxOccupancy(results.getInt("max_occupancy"));

        return newSpace;
    }

}
