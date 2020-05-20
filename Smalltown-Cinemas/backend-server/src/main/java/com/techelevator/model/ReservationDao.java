package com.techelevator.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface ReservationDao {
	
	public void addReservation(int thisUserId, int thisShowtimeId, int thisSeatId, String reservationType, double payment);
	
	public List<Reservation> viewAllReservations(String username);
	
	public Reservation getReservationById(int reservationId);
	
	public void updateReservation(Reservation thisReservation, Showtime newShowtime, Seat newSeat);
	
	public void deleteReservation(Reservation reservationToDelete);

	Reciept getMovie(int reservationId);

	Time getMovieTime(int reservationId);

	int getScreenNumber(int reservationId);

	Date getMovieDate(int reservationId);
}
