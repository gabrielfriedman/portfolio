package com.techelevator.controller;

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

import com.techelevator.model.Films;
import com.techelevator.model.FilmsDao;

@RestController
@CrossOrigin
@RequestMapping("api/films")

public class FilmController {
	
	@Autowired
	private FilmsDao films;
	
	@GetMapping
	public List<Films> getAllFilms(){
		return films.getAllFilms();
	}
	
	@GetMapping("/{id}")
	public Films getFilmById(@PathVariable int id) {
		return films.getFilmById(id);
	}
	
	@PostMapping                        
	@ResponseStatus(HttpStatus.CREATED)
	public Films addFilm(@RequestBody Films newFilm) {
		return films.addFilm(newFilm);
	}
	
}
