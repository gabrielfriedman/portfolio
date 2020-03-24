package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.reservation.*;
import com.techelevator.model.site.JdbcSiteDAO;
import com.techelevator.view.*;

public class CampgroundApp {
	
	

	
	
	
	/****************************************************************************************************
	 * This is the Campground Reservation system application program
	 * 
	 * Any and all user interactions should be placed in the CampgroundUI class 
	 *     which is in the com.techelevator.view package.
	 *     
	 * This application program should instantiate a CampgroundUI object 
	 *      and use its methods to interact with the user.
	 *      
	 * This application program should contain no user interactions.
	 * 
	 * Any and all database accesses should be placed in the appropriate
	 *     com.techelevator.model.tablename package component
	 *     
	 * This application program should instantiate DAO objects and use the methods
	 *     in the DAO to interact with the database tables.   
	 *     
	 * There should be no SQL in this application program
	 *   
	 ***************************************************************************************************/
	
	
	
	public static void main(String[] args) {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		
	
		
		/****************************************************************************************************
		 * Instantiate any DAOs you will be using here
		 ***************************************************************************************************/
		JdbcParkDAO parks = new JdbcParkDAO(dataSource);
		JdbcCampgroundDAO campgrounds = new JdbcCampgroundDAO(dataSource);
		JdbcSiteDAO sites = new JdbcSiteDAO(dataSource);
		JdbcReservationDAO reservations = new JdbcReservationDAO(dataSource);
		/****************************************************************************************************
		 * Your application programming logic goes here
		 ***************************************************************************************************/
		
		CampgroundUI userInterface = new CampgroundUI(parks, campgrounds, sites, reservations); 
		userInterface.run();
		
		
		
	} 
	
	
		
}	
	

