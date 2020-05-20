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
public class JdbcReservationDao implements ReservationDao {
	
	private JdbcTemplate myJdbcTemplate;
	
	public JdbcReservationDao(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource);
	}

	@Override
	public void addReservation(int thisUserId, int thisShowtimeId, int thisSeatId, String reservationType, double payment) {
		String sql = "insert into reservations (user_id, showtime_id, seat_id, reservation_type, payment) " +
					 "values(?,?,?,?,?)";
		myJdbcTemplate.update(sql, thisUserId, thisShowtimeId, thisSeatId, reservationType, payment);		
	}

	@Override
	public List<Reservation> viewAllReservations(String username) {
		String sql = "select * from reservations where user_id = (select id from users where username = ?) ";
		List<Reservation> reservationList = new ArrayList<>();
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, username);
		while(results.next()) {
			Reservation newReservation = mapRowToReservation(results);
			reservationList.add(newReservation);
		}
		return reservationList;
	}

	@Override
	public Reservation getReservationById(int reservationId) {
		String sql = "select * from reservations where reservation_id = ?";
		Reservation thisReservation = new Reservation();
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, reservationId);
		while(results.next()) {
			thisReservation = mapRowToReservation(results);	
		}
		return thisReservation;
	}

	@Override
	public void updateReservation(Reservation thisReservation, Showtime newShowtime, Seat newSeat) {
		String sql = "update reservations set showtime_id = ?, seat_id = ? where reservation_id = ?";
		myJdbcTemplate.update(sql, newShowtime.getShowtimeId(), newSeat.getSeatId(), thisReservation.getReservationId());	
	}

	@Override
	public void deleteReservation(Reservation reservationToDelete) {
		String sql = "delete from reservations where reservation_id = ?";
		myJdbcTemplate.update(sql, reservationToDelete.getReservationId());
	}
	
	
	@Override
	public Reciept getMovie(int reservationId) {
		Reciept movie = new Reciept();
		String sql = "select films.film_title as film, "
				   + "screens.screen_id as screen, "
				   + "showtimes.showing_date as date, "
				   + "showtimes.showing_time as time, "
				   + "seats.row_id as row, "
				   + "seats.row_seat_id as seat "
				   + "from films, showtimes, reservations, screens, seats "
				   + "where films.film_id = screens.film_id "
				   + "and screens.film_id = films.film_id " 
				   + "and screens.screen_id = showtimes.screen_id " 
				   + "and showtimes.showtime_id = reservations.showtime_id " 
				   + "and reservations.seat_id = seats.seat_id "
				   + "and reservation_id = ?";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, reservationId);
		if (results.next()) {
			movie.setFilmTitle(results.getString("film"));
			movie.setScreenId(results.getInt("screen"));
			movie.setShowingDate(results.getDate("date"));
			movie.setShowingTime(results.getTime("time"));
			movie.setSeatRow(results.getString("row"));
			movie.setSeatNumber(results.getInt("seat"));
		}
		return movie;	
	}
	@Override
	public Time getMovieTime(int reservationId) {
		Time time = null;
		String sql = "select films.film_title, screens.screen_id, showtimes.showing_date, showtimes.showing_time as time from films, showtimes, reservations, screens " + 
				"where films.film_id = screens.film_id " + 
				"and screens.film_id = films.film_id " + 
				"and screens.screen_id = showtimes.screen_id " + 
				"and showtimes.showtime_id = reservations.showtime_id " + 
				"and reservation_id = ?";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, reservationId);
		if (results.next()) {
			time = results.getTime("time");
		}
		return time;	
	}
	@Override
	public int getScreenNumber(int reservationId) {
		int screen = 0;
		String sql = "select films.film_title, screens.screen_id as screen, showtimes.showing_date, showtimes.showing_time from films, showtimes, reservations, screens " + 
				"where films.film_id = screens.film_id " + 
				"and screens.film_id = films.film_id " + 
				"and screens.screen_id = showtimes.screen_id " + 
				"and showtimes.showtime_id = reservations.showtime_id " + 
				"and reservation_id = ?";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, reservationId);
		if (results.next()) {
			screen = results.getInt("screen");
		}
		return screen;	
	}
	@Override
	public Date getMovieDate(int reservationId) {
		Date date = null;
		String sql = "select films.film_title, screens.screen_id, showtimes.showing_date as date, showtimes.showing_time from films, showtimes, reservations, screens " + 
				"where films.film_id = screens.film_id " + 
				"and screens.film_id = films.film_id " + 
				"and screens.screen_id = showtimes.screen_id " + 
				"and showtimes.showtime_id = reservations.showtime_id " + 
				"and reservation_id = ?";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, reservationId);
		if (results.next()) {
			date = results.getDate("date");
		}
		return date;	
	}
	
	
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation newReservation = new Reservation();
		newReservation.setReservationId(results.getInt("reservation_id"));
		newReservation.setUserId(results.getInt("user_id"));
		newReservation.setShowtimeId(results.getInt("showtime_id"));
		newReservation.setSeatId(results.getInt("seat_id"));
		newReservation.setReservationType(results.getString("reservation_type"));
		newReservation.setPayment(results.getDouble("payment"));
		
		return newReservation;
	}

}
