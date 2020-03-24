package com.techelevator.model.campground;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.park.Park;

public class JdbcCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JdbcCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> viewAllCampgrounds(Park selectedPark) {
		List<Campground> allCampgrounds = new ArrayList<Campground>();
		String sqlCode = "select * from campground where park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCode, selectedPark.getParkId());

		while (results.next()) {
			Campground theCampground = mapToCampground(results);
			allCampgrounds.add(theCampground);

		}
		return allCampgrounds;

	}

	@Override
	public List<Campground> viewAvailableCampgrounds(Park selectedPark) {
		List<Campground> availableCampgrounds = viewAllCampgrounds(selectedPark);

		int thisMonth = LocalDate.now().getMonthValue();

		for (int i = 0; i < availableCampgrounds.size(); i++) {
			if (availableCampgrounds.get(i).getOpenFromMonth() > thisMonth
					|| availableCampgrounds.get(i).getOpenToMonth() < thisMonth) {

				availableCampgrounds.remove(availableCampgrounds.get(i));

			}
		}

		return availableCampgrounds;
	}

	@Override
	public Campground selectCampground(Long campgroundId) {
		Campground thisCampground = null;
		String sqlCode = "select * from campground where campground_Id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCode, campgroundId);
		if (results.next()) {
			thisCampground = mapToCampground(results);
		}
		return thisCampground;
	}

	private Campground mapToCampground(SqlRowSet results) {
		Campground thisCampground = new Campground();
		thisCampground.setCampgroundId(results.getLong("campground_id"));
		thisCampground.setName(results.getString("name"));
		thisCampground.setParkId(results.getLong("park_id"));
		thisCampground.setOpenFromMonth(results.getInt("open_from_mm"));
		thisCampground.setOpenToMonth(results.getInt("open_to_mm"));
		thisCampground.setDailyFee(results.getDouble("daily_fee"));

		return thisCampground;
	}

}
