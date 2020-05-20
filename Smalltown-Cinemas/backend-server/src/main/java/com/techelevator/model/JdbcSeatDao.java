package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcSeatDao implements SeatDao {

	private JdbcTemplate myJdbcTemplate;
	
	public JdbcSeatDao(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource);
	}
	
	@Override
	public List<Seat> getAllSeats(Screens selectedScreen) {
		List<Seat> seatsList = new ArrayList<>();
		String sql = "select * from seats where screen_id = ?";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, selectedScreen.getScreenId());
		while (results.next()) {
			Seat thisSeat = mapRowToSeat(results);
			seatsList.add(thisSeat);
		}
	
		return seatsList;
	}

	@Override
	public Seat getSeatById(int seatId) {
		Seat thisSeat = new Seat();
		String sql = "select * from seats where seat_id = ?";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, seatId);
		while (results.next()) {
			thisSeat = mapRowToSeat(results);
		}
		
		return thisSeat;
	}
	
	@Override
	public List<Seat> getReservedSeats(int showtimeId) {
		String sql = "select seats.seat_id, seats.screen_id, seats.row_id, seats.row_seat_id from seats inner join reservations on reservations.seat_id = seats.seat_id " + 
					 "where reservations.showtime_id = ?";
		List<Seat> reservedSeats = new ArrayList<>();
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, showtimeId);
		while (results.next()) {
			reservedSeats.add(mapRowToSeat(results));
		}
		return reservedSeats;
	}
	
	private Seat mapRowToSeat(SqlRowSet results) {
		Seat thisSeat = new Seat();
		thisSeat.setSeatId(results.getInt("seat_id"));
		thisSeat.setScreenId(results.getInt("screen_id"));
		thisSeat.setRow(results.getString("row_id"));
		thisSeat.setSeatNumber(results.getInt("row_seat_id"));
		
		return thisSeat;
	}
	
}
