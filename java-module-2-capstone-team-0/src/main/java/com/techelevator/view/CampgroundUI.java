package com.techelevator.view;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.model.campground.Campground;
import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.park.Park;
import com.techelevator.model.reservation.JdbcReservationDAO;
import com.techelevator.model.reservation.Reservation;
import com.techelevator.model.site.JdbcSiteDAO;
import com.techelevator.model.site.Site;

public class CampgroundUI {

	Scanner keyboard = new Scanner(System.in);

	private JdbcParkDAO parks;
	private JdbcCampgroundDAO campgrounds;
	private JdbcSiteDAO sites;
	private JdbcReservationDAO reservations;

	private Park selectedPark;
	private Campground selectedCampground;
	private LocalDate arrivalDate;
	private LocalDate departureDate;
	private Site selectedSite;
	private String camperName;
	private Reservation selectedReservation;
	private DecimalFormat df = new DecimalFormat("#.00");

	public CampgroundUI(JdbcParkDAO parks, JdbcCampgroundDAO campgrounds, JdbcSiteDAO sites,
			JdbcReservationDAO reservations) {
		this.parks = parks;
		this.campgrounds = campgrounds;
		this.sites = sites;
		this.reservations = reservations;
	}

	private int parkMenu() { // loop level 1
		int i = 1;
		Map<Integer, Park> parkOptions = new HashMap<Integer, Park>(); // create map that contains options(i) and their
																		// corresponding park
		System.out.println();
		System.out.println();
		System.out.println("Park Selection Menu");
		System.out.println("*******************");
		System.out.println("Select a Park: ");
		for (Park p : parks.viewAllParks()) {
			System.out.println(i + ") " + p.getName());
			parkOptions.put(i, p);
			i++;
		}
		System.out.println("Q) Quit");
		System.out.println("Select a park");
		String choice = keyboard.nextLine();
		try {
			if (choice.toLowerCase().contains("q")) { // if they choose quit then app ends
				return 0; // app ends by setting the looplevel below the threshold needed during the run
							// method
			} else {
				int choiceNum = Integer.parseInt(choice);
				Park chosenPark = parkOptions.get(choiceNum); // set the chosen park for the option they chose
				selectedPark = chosenPark; // set chosen park
				return 2; // advance to next menu level
			}
		} catch (NumberFormatException e) {
			badSelection();
			return 1;

		}

	}

	private int campgroundMenu() { // loop level 2
		final String option1 = "1";
		final String option2 = "2";
		
		giveMeSomeSpace();
		System.out.println("*************************************");
		System.out.println("Park Information Screen");
		System.out.println("*************************************");
		System.out.println(selectedPark.getName() + " National Park");
		System.out.println("Location:	" + selectedPark.getLocation());
		System.out.println("Established: 	" + selectedPark.getEstablishDate());
		System.out.println("Area:	" + selectedPark.getArea() + " sq km");
		System.out.println();

		StringBuilder description = new StringBuilder(selectedPark.getDescription());  // Makes a mutable string object
		int i = 0;
		while (i + 75 < description.length() && (i = description.lastIndexOf(" ", i + 75)) != -1) {
			description.replace(i, i + 1, "\n");   	 // Takes the object and replaces a space with a newline character
		}											 // at the last space encountered before every 75 characters
		System.out.println(description.toString()); 
		System.out.println();
		System.out.println(option1 + ") View Campgrounds");
		System.out.println(option2 + ") Return to Previous Screen");
		
		String choice = keyboard.nextLine();

		try {
			switch (choice) {
			case option1:
				return 3; // goes to view all campgrounds method, level 3
			case option2:
				return 1; // returns to the select national park screen
			default:
				return 2; // if anything other than the given options were selected, then we will stay in
							// this menu
			}
		} catch (NumberFormatException e) {
			badSelection();
			return 2;
		}
	}

	private int viewAllCampgrounds() { // loop level 3

		Map<Integer, Campground> campgroundOptions = new HashMap<Integer, Campground>();

		int i = 1;

		giveMeSomeSpace();
		System.out.println("*********************************");
		System.out.println("Park Campgrounds");
		System.out.println(selectedPark.getName() + " National Park Campgrounds");

		System.out.println("      Name                               Open        Close       Daily Fee");
		for (Campground c : campgrounds.viewAllCampgrounds(selectedPark)) { // build a map with option(i) and
																			// corresponding campgrounds for the
																			// selected park

			System.out.println("#" + String.format("%-5s", i) + String.format("%-35s", c.getName())
					+ String.format("%-12s", toMonthString(c.getOpenFromMonth()))
					+ String.format("%-12s", toMonthString(c.getOpenToMonth()))
					+ String.format("%-8s", "$" + df.format(c.getDailyFee()))); // we need to fill in the rest of the
																				// campground info
			campgroundOptions.put(i, c);
			i++;
		}

		final String option1 = "1";
		final String option2 = "2";

		System.out.println();
		System.out.println(option1 + ") Search for Available Reservations");
		System.out.println(option2 + ") Return to Previous Screen");

		String choice = keyboard.nextLine();
		try {
			switch (choice) {
			case option1:
				return 4; // move to view available campgrounds
			case option2:
				return 2; // go back to the original campground menu
			default:
				return 3; // stay in this menu if none of the given options were selected
			}
		} catch (NumberFormatException e) {
			badSelection();
			return 2;
		}

	}

	private int viewAvailableCampgrounds() { // loop level 4

		Map<Integer, Campground> campgroundOptions = new HashMap<Integer, Campground>();

		giveMeSomeSpace();
		System.out.println("**********************");
		System.out.println("Campground Search Menu");
		System.out.println("**********************");

		int i = 1;
		for (Campground c : campgrounds.viewAllCampgrounds(selectedPark)) { // create map of only campgrounds in
																			// season
			System.out.println(i + ") " + c.getName());
			campgroundOptions.put(i, c);
			i++;
		}
		System.out.println();
		System.out.println("0 To Return to Previous Screen");
		System.out.println();
		System.out.print("Which campground would you like to search?"); // select campground

		String choice = keyboard.nextLine();
		try {
			if (campgroundOptions.containsKey(Integer.parseInt(choice))) { // if they choose a valid option, move on
				selectedCampground = campgroundOptions.get(Integer.parseInt(choice)); // set selected campground, then
																						// retrieve arrival
				// and
				// departure dates
				System.out.println("Arrival Date(yyyy-mm-dd): ");
				String arrivalDateString = keyboard.nextLine();
				arrivalDate = LocalDate.parse(arrivalDateString);
				System.out.println("Departure Date(yyyy-mm-dd): ");
				String departureDateString = keyboard.nextLine();
				departureDate = LocalDate.parse(departureDateString);

				return 5; // move on to set reservation
			} else {
				return 2; // otherwise return to campground menu
			}
		} catch (NumberFormatException e) {
			badSelection();
			return 4;
		} catch (DateTimeParseException e) {
			giveMeSomeSpace();
			System.out.println("Please enter date using the formatting convention yyyy-mm-dd");
			return 4;
		}
	}

	private int setReservation() { // looplevel 5

		Map<Integer, Site> siteOptions = new HashMap<Integer, Site>();
		List<Site>availableSites = sites.viewAllSitesByDateRange(selectedCampground, arrivalDate, departureDate);
		int lengthOfStay = (int) (departureDate.toEpochDay() - arrivalDate.toEpochDay());
		
		if (arrivalDate != departureDate) { // This is to account for someone arriving and leaving on the same date.
			lengthOfStay++;					// Otherwise the Period.between calculation is not inclusive and we 
		}									// need to add a day.
		
		if (availableSites.size() == 0) {  //if no sites are available, return to the campground selection menu
			giveMeSomeSpace();
			System.out.println("There are no sites available for the given date range.");
			System.out.println("Please choose different dates.");
			giveMeSomeSpace();
			return 4;
		}
		
		else if(arrivalDate.isAfter(departureDate)) {  //check that the arrival date is at or before the departure date
			giveMeSomeSpace();
			System.out.print("Error, arrival date must be prior to departure date.");
			giveMeSomeSpace();
			return 4;
		}
		
		else if (arrivalDate.isBefore(LocalDate.now())) {  //check that the arrival date is set in today or the future
			giveMeSomeSpace();
			System.out.print("Please select a date range that has not passed");
			giveMeSomeSpace();
			return 4;
		}

		else {  //otherwise give a list of sites to choose from
			
			System.out.println("Available sites:");
			for (Site s : availableSites) {
				System.out.println("Campsite " + s.getSiteNumber());
				siteOptions.put(s.getSiteNumber(), s);
			}
		
			System.out.println("Total cost for " + lengthOfStay + " day stay is $" + df.format((lengthOfStay * selectedCampground.getDailyFee())));
			System.out.println();
			System.out.print("Which site? (enter 0 to cancel)?");
			try {
				String choice = keyboard.nextLine();

				if (siteOptions.containsKey(Integer.parseInt(choice))) {
					selectedSite = siteOptions.get(Integer.parseInt(choice));
					System.out.println("Name for Reservation?");
					camperName = keyboard.nextLine();
					reservations.setReservation(selectedSite, camperName, arrivalDate, departureDate);
					selectedReservation = reservations.getReservationObject(selectedSite.getSiteId(), camperName,
							arrivalDate, departureDate);
					System.out.println("Thank you " + selectedReservation.getName() + ", your reservation id is "
							+ selectedReservation.getReservationId());
				
					System.out.println();
					return 1;
				} else {
					return 4;
				}
			} catch (NumberFormatException e) {
				badSelection();
				return 5;
			}
		}
	}

	public void run() {
		displayBanner();
		int loopLevel = 1;
		while (loopLevel >= 1) {
			loopLevel = parkMenu();

			while (loopLevel >= 2) {
				loopLevel = campgroundMenu();

				while (loopLevel == 3) {
					loopLevel = viewAllCampgrounds();

					while (loopLevel == 4) {
						loopLevel = viewAvailableCampgrounds();

							while (loopLevel == 5) {
								loopLevel = setReservation();
							}
						}
					}
				}
			}
		displayGoodbye();
		}
		
	

	private void badSelection() {
		giveMeSomeSpace();
		System.out.println("That was not a valid selection");
		giveMeSomeSpace();
	}

	private String toMonthString(Integer monthAsInt) {  // This method will let the open/close months show as text
		switch (monthAsInt) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		}
		return monthAsInt.toString();  // If for some reason there is a month that is not one of the known 12 months, 
	}                                  // it will return as an integer

	private void giveMeSomeSpace() {   // This is to separate out large blocks of text
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}

	private void displayBanner() {  // Note, this has not actually been fact checked
		System.out.println("**************************************************************");
		System.out.println("       Welcome to the National Park reservation system.       ");
		System.out.println("Now featuring a 50% year over year reduction in bear maulings!");
		System.out.println("**************************************************************");
	}

	private void displayGoodbye() {
		giveMeSomeSpace();
		System.out.println("                      ________________________________");
		System.out.println("                     /                                \\     ");
		System.out.println("                    /                                  \\ ");
		System.out.println("                   /                                    \\ ");
		System.out.println("                  /                                      \\ ");
		System.out.println("                 /    ________________________________    \\ ");
		System.out.println("                /    /                                \\    \\ ");
		System.out.println("               |    /     Thank you for visiting!      \\    |");
		System.out.println("               |    \\        Have a nice day!          /    |");
		System.out.println("               |     \\________________________________/     |");
		System.out.println("               |                                            |");
		System.out.println("                \\                                          /");
		System.out.println("                 \\                                        /");
		System.out.println("                  \\                             __       /");
		System.out.println("                   \\       ___       ___       / /_     /");
		System.out.println("                    \\      \\ \\       | |     \\/ /      /");
		System.out.println("                     \\      \\ \\  ___ | |\\    / /\\     /");
		System.out.println("                      \\______\\ \\/   \\| | \\__/ /______/");
		System.out.println("                              \\ \\    | |   / /");
		System.out.println("                              |          \\  |");
		System.out.println("                              |      |      |");
		System.out.println("                              |    / |   \\  |");
		System.out.println("                              |             |");
		System.out.println("                              |        \\    |");
		System.out.println("                              |             |");
		System.out.println("                              |     |    |  |");
		System.out.println("                              |   /   |     |");
		System.out.println("                              |             |");
		System.out.println("                              |   /     \\   |");
		System.out.println("                              |             | ");
		System.out.println("                              |    /        |");
		System.out.println("                              |         \\   |");
		System.out.println("                             /  /         \\  \\ ");
		System.out.println("                    ________/                 \\________");

	}

}
