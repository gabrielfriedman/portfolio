package com.techelevator.controller;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.Reciept;
import com.techelevator.model.Reservation;
import com.techelevator.model.ReservationDao;
import com.techelevator.model.Seat;
import com.techelevator.model.Showtime;
import com.techelevator.model.User;

@RestController
@CrossOrigin
@RequestMapping("api/reservations")
public class ReservationController {
	
	
	@Autowired
	private ReservationDao reservation;
	
	@PostMapping(path = "/makeReservation")
	public void save(@RequestBody Reservation thisReservation) {
		reservation.addReservation(thisReservation.getUserId(), thisReservation.getShowtimeId(), 
		thisReservation.getSeatId(), thisReservation.getReservationType(), thisReservation.getPayment());
	}
	
	@RequestMapping(path = "/getReservations/{username}", method = RequestMethod.GET)
	public List<Reservation> getAllReservations(@PathVariable String username){
		return reservation.viewAllReservations(username);	
	}
	
	@GetMapping(path = "/getMovie/{reservationId}")
	public Reciept getMovie(@PathVariable int reservationId) {
		return reservation.getMovie(reservationId);
	}
	
	@GetMapping(path = "/getScreen/{reservationId}")
	public int getScreen(@PathVariable int reservationId) {
		return reservation.getScreenNumber(reservationId);
	}
	
	@GetMapping(path = "/getDate/{reservationId}")
	public Date getDate(@PathVariable int reservationId) {
		return reservation.getMovieDate(reservationId);
	}
	
	@GetMapping(path = "/getTime/{reservationId}")
	public Time getTime(@PathVariable int reservationId) {
		return reservation.getMovieTime(reservationId);
	}

}
