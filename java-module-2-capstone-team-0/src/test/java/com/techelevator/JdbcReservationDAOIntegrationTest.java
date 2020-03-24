package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.model.campground.Campground;
import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.reservation.JdbcReservationDAO;
import com.techelevator.model.reservation.Reservation;
import com.techelevator.model.site.JdbcSiteDAO;
import com.techelevator.model.site.Site;

public class JdbcReservationDAOIntegrationTest extends DAOIntegrationTest {

	private DataSource dataSource = getDataSource();
	private JdbcReservationDAO testReservation;
	private JdbcSiteDAO testSite;
	private JdbcCampgroundDAO testCampground;
	private Reservation thisReservation;
	private Site thisSite;
	private Campground thisCampground;
	
	
	
	@Before
	public void setup() {
		String sqlInsertReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date, create_date)" + 
								"VALUES (0, 1, 'Oscar the Grouch', '2020-07-01', '2020-07-15', ?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertReservation, LocalDate.now());
		testReservation = new JdbcReservationDAO(dataSource);
		testCampground = new JdbcCampgroundDAO(dataSource);
		testSite = new JdbcSiteDAO(dataSource);
		thisCampground = testCampground.selectCampground((long) 1);
		thisSite = testSite.viewAllSitesByCampgroundID(thisCampground).get(0);
		
	}
	
	@Test
	public void test_get_reservation_object() {
		thisReservation = testReservation.getReservationObject(1, "Oscar the Grouch", LocalDate.parse("2020-07-01") , LocalDate.parse("2020-07-15"));
		assertEquals("the reservation object should have a reservation_id of 0", 0, thisReservation.getReservationId());
	}
	
	@Test
	public void test_set_reservation() {
		testReservation.setReservation(thisSite, "Oscar the Grouch",LocalDate.parse("2020-06-01"), LocalDate.parse("2020-06-15"));
		thisReservation = testReservation.getReservationObject(thisSite.getSiteId(), "Oscar the Grouch", LocalDate.parse("2020-06-01"), LocalDate.parse("2020-06-15"));
		assertEquals("The method should have set a reservation for Oscar the Grouch", "Oscar the Grouch", thisReservation.getName());
	}
}
