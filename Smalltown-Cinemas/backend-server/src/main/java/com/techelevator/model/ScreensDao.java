package com.techelevator.model;

import java.util.List;

public interface ScreensDao {
	
	public void createScreen(int newScreenNumber);
	
	public List<Screens> viewAllScreens();
	
	public Screens getScreenById(int screenId);
	
	public Screens updateScreen(Screens thisScreen, Films newFilm);
	
	public void deleteScreen(Screens screenToDelete);

	int getScreenIdByMovieId(int movieId);
	
}
