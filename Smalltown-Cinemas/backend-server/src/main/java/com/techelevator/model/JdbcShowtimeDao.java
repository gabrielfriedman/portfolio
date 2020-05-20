package com.techelevator.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcShowtimeDao implements ShowtimeDao {
	
	private JdbcTemplate myJdbcTemplate;
	
	public JdbcShowtimeDao(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource);
	}

	@Override
	public void addShowtime(Screens showingScreen, Date showingDate, Time showingTime) {
		String sql = "insert into showtimes (screen_id, showing_date, showing_time) " +
				     "values(?,?,?)";
		myJdbcTemplate.update(sql, showingScreen.getScreenId(), showingDate, showingTime);	
		
	}
	
	

	@Override
	public List<Showtime> getAllShowtimes(Screens chosenScreen) {
		String sql = "select * from showtimes where screen_id = ?";
		List<Showtime> showtimeList = new ArrayList<>();
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, chosenScreen.getScreenId());
		while (results.next()) {
			Showtime thisShowtime = mapRowToShowtime(results);
			showtimeList.add(thisShowtime);
		}
		
		return showtimeList;
	}

	@Override
	public Showtime getShowtimeById(int showtimeId) {
		String sql = "select * from showtimes where showtime_id = ?";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, showtimeId);
		Showtime thisShowtime = new Showtime();
		if (results.next() ) {
			thisShowtime = mapRowToShowtime(results);
		}
		return thisShowtime;
	} 

	@Override
	public void updateShowtime(Showtime thisShowtime, Date newDate, Time newTime) {
		String sql = "update showtimes set showing_date = ?, showing_time = ? " +
					"where showtime_id = ?";
		myJdbcTemplate.update(sql, newDate, newTime, thisShowtime.getShowtimeId());
	}

	@Override
	public void deleteShowtime(Showtime showtimeToDelete) {
		String sql = "delete from showtimes where showtime_id = ?";
		myJdbcTemplate.update(sql, showtimeToDelete.getShowtimeId());
	}
	
	private Showtime mapRowToShowtime(SqlRowSet results) {
		Showtime newShowtime = new Showtime();
		newShowtime.setShowtimeId(results.getInt("showtime_id"));
		newShowtime.setScreenId(results.getInt("screen_id"));
		newShowtime.setShowingDate(results.getDate("showing_date"));
		newShowtime.setShowingTime(results.getTime("showing_time"));
		
		return newShowtime;
	}
	
	

}
