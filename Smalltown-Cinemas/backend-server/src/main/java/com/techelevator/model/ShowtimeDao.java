package com.techelevator.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface ShowtimeDao {
	
	public void addShowtime(Screens showingScreen, Date showingDate, Time showingTime);
	
	public List<Showtime> getAllShowtimes(Screens chosenScreen);
	
	public Showtime getShowtimeById(int showtimeId);
	
	public void updateShowtime(Showtime thisShowtime, Date newDate, Time newTime);
	
	public void deleteShowtime(Showtime showtimetoDelete);

}
