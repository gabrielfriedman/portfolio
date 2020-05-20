package com.techelevator.model;

import java.util.List;

public interface SeatDao {
	
	public List<Seat> getAllSeats(Screens selectedScreen);
	
	public Seat getSeatById(int seatId);

	List<Seat> getReservedSeats(int showtimeId);

}
