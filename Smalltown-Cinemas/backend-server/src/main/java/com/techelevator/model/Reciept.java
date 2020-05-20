package com.techelevator.model;

import java.sql.Time;
import java.util.Date;

public class Reciept {
	
	private String filmTitle;
	private int screenId;
	private Date showingDate;
	private Time showingTime;
	private String seatRow;
	private int seatNumber;
	
	
	public String getSeatRow() {
		return seatRow;
	}
	public void setSeatRow(String seatRow) {
		this.seatRow = seatRow;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getFilmTitle() {
		return filmTitle;
	}
	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
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
