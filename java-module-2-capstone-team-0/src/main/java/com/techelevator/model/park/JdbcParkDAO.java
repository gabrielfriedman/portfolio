package com.techelevator.model.park;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcParkDAO implements ParkDAO {

	private JdbcTemplate myJdbcTemplate;

	public JdbcParkDAO(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource);
	}

	@Override
	public List<Park> viewAllParks() {
		String sqlViewParks = "select * from park";
		List<Park> parkList = new ArrayList<Park>();
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sqlViewParks);
		while (results.next()) {
			Park thePark = mapRowToPark(results);
			parkList.add(thePark);
		}
		return parkList;
	}

	@Override
	public Park selectPark(long parkId) {
		Park thisPark = null;
		String sqlSelectPark = "select * from park where park_id = ?";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sqlSelectPark, parkId);
		if (results.next()) {
			thisPark = mapRowToPark(results);
		}
		return thisPark;
	}

	private Park mapRowToPark(SqlRowSet results) {
		Park thePark = new Park();
		thePark.setParkId(results.getLong("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		thePark.setArea(results.getInt("area"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}

}
