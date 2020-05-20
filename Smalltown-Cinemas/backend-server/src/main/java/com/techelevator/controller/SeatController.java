package com.techelevator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.ScreensDao;
import com.techelevator.model.Seat;
import com.techelevator.model.SeatDao;

@RestController
@CrossOrigin
@RequestMapping("api/seats")
public class SeatController {
	
	@Autowired
	private SeatDao seats;
	
	@Autowired
	private ScreensDao screens;
	
	@GetMapping("/{screenId}")
	public List<Seat> getAllSeatsByScreen(@PathVariable int screenId) {
		return seats.getAllSeats(screens.getScreenById(screenId));
	}
	
	@GetMapping("/reservedSeats/{showtimeId}")
	public List<Seat> getReservedSeats(@PathVariable int showtimeId) {
		return seats.getReservedSeats(showtimeId);
	}

}
