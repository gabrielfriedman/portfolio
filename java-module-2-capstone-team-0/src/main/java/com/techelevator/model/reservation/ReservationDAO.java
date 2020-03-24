package com.techelevator.model.reservation;

import java.time.LocalDate;

import com.techelevator.model.site.Site;

public interface ReservationDAO {
	

	public void setReservation(Site thisReservationSite, String name, LocalDate arrivalDate, LocalDate departureDate);
		
	public Reservation getReservationObject(long siteId, String name, LocalDate arrivalDate, LocalDate departureDate);
}
