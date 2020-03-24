package com.techelevator.model.campground;

import java.util.List;

import com.techelevator.model.park.Park;

public interface CampgroundDAO {

	public List<Campground> viewAllCampgrounds(Park selectedPark);
	
	public List<Campground> viewAvailableCampgrounds(Park selectedPark);
	
	public Campground selectCampground(Long selectedCampground);
	
}
