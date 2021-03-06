package com.techelevator.model;

import java.sql.Time;
import java.util.Date;

public class Showtime {
	
	private int showtimeId;
	private int screenId;
	private Date showingDate;
	private Time showingTime;
	
	public int getShowtimeId() {
		return showtimeId;
	}
	
	public void setShowtimeId(int showtimeId) {
		this.showtimeId = showtimeId;
	}
	public int getScreenId() {
		return screenId;
	}
	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}
	public Date getShowingDate() {
		return showingDate;
	}
	public void setShowingDate(Date showingDate) {
		this.showingDate = showingDate;
	}
	public Time getShowingTime() {
		return showingTime;
	}
	public void setShowingTime(Time showingTime) {
		this.showingTime = showingTime;
	}
}
