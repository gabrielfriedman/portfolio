package com.techelevator.model.park;

import java.util.List;

public interface ParkDAO {

	public List<Park> viewAllParks();
	
	public Park selectPark(long parkId);
	
}
