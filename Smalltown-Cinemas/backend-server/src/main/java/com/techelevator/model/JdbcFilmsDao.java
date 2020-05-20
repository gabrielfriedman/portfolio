package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcFilmsDao implements FilmsDao {

	private JdbcTemplate myJdbcTemplate;
	
	public JdbcFilmsDao(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource);
	}
	
	
	@Override
	public Films addFilm(Films newFilm) {
		String sql = "insert into films (film_id, film_title) values(?,?)";
		myJdbcTemplate.update(sql, newFilm.getFilmId(), newFilm.getFilmTitle());	
		return getFilmById(newFilm.getFilmId());
	}

	@Override
	public List<Films> getAllFilms() {
		String sql = "select * from films";
		List<Films> filmList = new ArrayList<>();
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			Films thisFilm = mapRowToFilm(results);
			filmList.add(thisFilm);
		}
		
		return filmList;
	}
	
	@Override
	public Films getFilmById(int filmId) {
		String sql = "select * from films where film_id = ?";
		Films thisFilm = new Films();
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sql, filmId);
		while(results.next()) {
			thisFilm = mapRowToFilm(results);
		}
		return thisFilm;
	}

	@Override
	public Films updateFilmName(Films film, String newName) {
		String sql = "update films set film_title = ? where film_id = ?";
		myJdbcTemplate.update(sql, newName, film.getFilmId());
		return getFilmById(film.getFilmId());
		
	}

	@Override
	public void deleteFilm(Films filmToDelete) {
		String sql = "delete from films where film_id = ?";
		myJdbcTemplate.update(sql, filmToDelete.getFilmId());
	
	}
	
	private Films mapRowToFilm(SqlRowSet results) {
		Films thisFilm = new Films();
		thisFilm.setFilmId(results.getInt("film_id"));
		thisFilm.setFilmTitle(results.getString("film_title"));
		
		return thisFilm;
	}
	
}
