package com.techelevator.controller;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.Screens;
import com.techelevator.model.ScreensDao;
import com.techelevator.model.Showtime;
import com.techelevator.model.ShowtimeDao;

@RestController
@CrossOrigin
@RequestMapping("api/showtimes")
public class ShowtimesController {
	
	@Autowired
	private ShowtimeDao showtimes;
	
	@Autowired
	private ScreensDao screens;
	
	@GetMapping("/{screenId}") 
	public List<Showtime> getShowtimesByScreen(@PathVariable int screenId) {
		return showtimes.getAllShowtimes(screens.getScreenById(screenId));
	}
	
	@GetMapping("/showtimeId/{id}")
	public Showtime getShowtimeById(@PathVariable int id) {
		return showtimes.getShowtimeById(id);
	}
	
	@PostMapping("/{screenId}" )                     
	@ResponseStatus(HttpStatus.CREATED)
	public void addShowtime(@PathVariable int screenId, @RequestBody Date showingDate, @RequestBody Time showingTime) {
		showtimes.addShowtime(screens.getScreenById(screenId), showingDate, showingTime);
	}
}
