package com.techelevator.model;

import java.util.List;

public interface FilmsDao {
	
	public Films addFilm(Films newFilm);
	
	public List<Films> getAllFilms();
	
	public Films getFilmById(int filmId);
	
	public Films updateFilmName(Films film, String newName);
	
	public void deleteFilm(Films filmToDelete);
	

}
