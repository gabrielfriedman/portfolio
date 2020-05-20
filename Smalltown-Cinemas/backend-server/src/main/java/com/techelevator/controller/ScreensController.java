package com.techelevator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.Films;
import com.techelevator.model.Screens;
import com.techelevator.model.ScreensDao;

@RestController
@CrossOrigin
@RequestMapping("api/screens")

public class ScreensController {
	
	@Autowired
	private ScreensDao screens;
	
	@GetMapping
	public List<Screens> getAllScreens(){
		return screens.viewAllScreens();
	}
	
	@GetMapping("/{id}")
	public Screens getScreenById(@PathVariable int id) {
		return screens.getScreenById(id);
	}

	@PutMapping("/{id}")
	public Screens updateScreen(@PathVariable int id, @RequestBody Films newFilm) {
		return screens.updateScreen(screens.getScreenById(id), newFilm);
	}
	
	@GetMapping("/movie/{id}")
	public int getScreenIdByMovie(@PathVariable int id) {
		return screens.getScreenIdByMovieId(id);
	}

}
