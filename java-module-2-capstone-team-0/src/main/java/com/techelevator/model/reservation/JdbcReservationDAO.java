package com.techelevator.model.reservation;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.site.Site;

public class JdbcReservationDAO implements ReservationDAO {

	private JdbcTemplate myJdbcTemplate;

	public JdbcReservationDAO(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource);
	}

	@Override
	public void setReservation(Site thisReservationSite, String name, LocalDate arrivalDate, LocalDate departureDate) {
		String sqlMakeReservation = "insert into reservation (site_id, name, from_date, to_date, create_date) values(?,?,?,?,?)";
		myJdbcTemplate.update(sqlMakeReservation, thisReservationSite.getSiteId(), name, arrivalDate, departureDate,
				LocalDate.now());
	}
	@Override
	public Reservation getReservationObject(long siteId, String name, LocalDate arrivalDate, LocalDate departureDate) {

		String getReservationObjectString = "select * from reservation where site_id = ? and name = ? and from_date = ? and to_date = ?";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(getReservationObjectString, siteId, name, arrivalDate,
				departureDate);

		Reservation thisReservation = new Reservation();
		if (results.next()) {

			thisReservation.setCreateDate(results.getDate("create_date").toLocalDate());
			thisReservation.setFromDate(results.getDate("from_date").toLocalDate());
			thisReservation.setToDate(results.getDate("to_date").toLocalDate());
			thisReservation.setName(results.getString("name"));
			thisReservation.setSiteId(results.getLong("site_id"));
			thisReservation.setReservationId(results.getLong("reservation_id"));
		}
		return thisReservation;
	}
}
