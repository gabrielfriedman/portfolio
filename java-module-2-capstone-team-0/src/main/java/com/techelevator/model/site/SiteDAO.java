package com.techelevator.model.site;

import java.time.LocalDate;
import java.util.List;

import com.techelevator.model.campground.Campground;

public interface SiteDAO {
	
	public List<Site> viewAllSitesByCampgroundID(Campground selectedCampground);
	
	public List<Site> viewAllSitesByDateRange(Campground selectedCampground, LocalDate arrivalDate, LocalDate departureDate);
	
}
