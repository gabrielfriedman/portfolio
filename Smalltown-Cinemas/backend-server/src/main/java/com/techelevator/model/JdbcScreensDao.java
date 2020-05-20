package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcScreensDao implements ScreensDao {

	private JdbcTemplate myJdbcTemplate;
	
	public JdbcScreensDao(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource);
	}
	
	@Override
	public void createScreen(int newScreenNumber) {
		String sql = "insert into Screens (?, film_id) values(null)";
		myJdbcTemplate.update(sql, newScreenNumber);
		
	}

	@Override
	public List<Screens> viewAllScreens() {
		String sql = "select * from screens";
		List<Screens> screenList = new ArrayList<>();
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			Screens thisScreen = mapRowToScreen(results);
			screenList.add(thisScreen);
		}
		return screenList;
	}
	
	@Override
	public Screens getScreenById(int screenId) {
		String sql = "select * from screens where screen_id = ?";
		Screens thisScreen = new Screens();
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, screenId);
		if (results.next()) {
			thisScreen = mapRowToScreen(results);
		}
		return thisScreen;
	}

	@Override
	public Screens updateScreen(Screens thisScreen, Films newFilm) {
		String sql = "update screens set film_id = ? where screen_id = ?";
		myJdbcTemplate.update(sql, newFilm.getFilmId(), thisScreen.getScreenId());
		return getScreenById(thisScreen.getScreenId());
		
	}

	@Override
	public void deleteScreen(Screens screenToDelete) {
		String sql = "delete from screens where screen_id = ?";
		myJdbcTemplate.update(sql, screenToDelete.getScreenId());
		
	}
	
	@Override 
	public int getScreenIdByMovieId(int movieId) {
		String sql = "select * from screens where film_id = ?";
		int screenNumber = 0;
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, movieId);
		if (results.next()) {
			screenNumber = results.getInt("screen_id");
		}
		return screenNumber;
	}
	
	private Screens mapRowToScreen(SqlRowSet results) {
		Screens thisScreen = new Screens();
		thisScreen.setFilmId(results.getInt("film_id"));
		thisScreen.setScreenId(results.getInt("screen_id"));
		
		return thisScreen;
	}

	

}
