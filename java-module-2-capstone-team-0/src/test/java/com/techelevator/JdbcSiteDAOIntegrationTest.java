package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.model.campground.Campground;
import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.reservation.JdbcReservationDAO;
import com.techelevator.model.reservation.Reservation;
import com.techelevator.model.site.JdbcSiteDAO;
import com.techelevator.model.site.Site;

public class JdbcSiteDAOIntegrationTest extends DAOIntegrationTest {

	private DataSource dataSource = getDataSource();
	private JdbcCampgroundDAO testCampground;
	private JdbcSiteDAO testSite;
	private JdbcReservationDAO testReservation;
	private Campground thisCampground;
	
	
	@Before
	public void setup() {
		String sqlInsertCampground = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) " + 
				"VALUES (1000, 1, 'Garbage Island', 05, 09, 5.00)";
		String sqlInsertSite1 = "INSERT INTO site (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) " + 
				"VALUES (1000, 1000, 0, 5, false, 0, false)";
		String sqlInsertSite2 = "INSERT INTO site (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) " + 
				"VALUES (1001, 1000, 1, 5, false, 0, false)";
		String sqlInsertSite3 = "INSERT INTO site (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) " + 
				"VALUES (1002, 1000, 2, 5, false, 0, false)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertCampground);
		jdbcTemplate.update(sqlInsertSite1);
		jdbcTemplate.update(sqlInsertSite2);
		jdbcTemplate.update(sqlInsertSite3);
		testSite = new JdbcSiteDAO(dataSource);
		testCampground = new JdbcCampgroundDAO(dataSource);
		testReservation = new JdbcReservationDAO(dataSource);
		thisCampground = testCampground.selectCampground((long) 1000);
				
	}
	
	@Test
	public void test_view_all_sites_by_campground_id() {
		List<Site> allSites = testSite.viewAllSitesByCampgroundID(thisCampground);
		assertFalse("List of all campgrounds should not be empty", allSites.isEmpty());
		assertEquals("list of all campgrounds should contain site 0", 0, allSites.get(0).getSiteNumber());
	}
	
	@Test
	public void test_view_available_sites_by_date_range() {
		List<Site> allSites = testSite.viewAllSitesByCampgroundID(thisCampground);
		testReservation.setReservation(allSites.get(0), "Oscar the Grouch", LocalDate.parse("2020-07-01"), LocalDate.parse("2020-07-28"));
		List<Site> availableSitesNotConflicting = testSite.viewAllSitesByDateRange(thisCampground, LocalDate.parse("2020-08-01"), LocalDate.parse("2020-08-28"));
		List<Site> availableSitesConflicting1 = testSite.viewAllSitesByDateRange(thisCampground, LocalDate.parse("2020-07-15"), LocalDate.parse("2020-08-15"));
		List<Site> availableSitesConflicting2 = testSite.viewAllSitesByDateRange(thisCampground, LocalDate.parse("2020-06-28"), LocalDate.parse("2020-07-15"));
		List<Site> availableSitesConflicting3 = testSite.viewAllSitesByDateRange(thisCampground, LocalDate.parse("2020-06-28"), LocalDate.parse("2020-08-15"));
		List<Site> availableSitesOutofRange1 = testSite.viewAllSitesByDateRange(thisCampground, LocalDate.parse("2020-04-28"), LocalDate.parse("2020-05-15"));
		List<Site> availableSitesOutofRange2 = testSite.viewAllSitesByDateRange(thisCampground, LocalDate.parse("2020-02-28"), LocalDate.parse("2020-03-15"));
		List<Site> availableSitesOutofRange3 = testSite.viewAllSitesByDateRange(thisCampground, LocalDate.parse("2020-10-28"), LocalDate.parse("2020-11-15"));
		
		assertEquals("List of Sites where dates do not conflict with Oscar the Grouch should contain site 0", 0, availableSitesNotConflicting.get(0).getSiteNumber());
		assertNotEquals("List of Sites where dates do conflict with Oscar the Grouch should not contain site 0", 0, availableSitesConflicting1.get(0).getSiteNumber());
		assertNotEquals("List of Sites where dates do conflict with Oscar the Grouch should not contain site 0", 0, availableSitesConflicting2.get(0).getSiteNumber());
		assertNotEquals("List of Sites where dates do conflict with Oscar the Grouch should not contain site 0", 0, availableSitesConflicting3.get(0).getSiteNumber());
		assertTrue("List of Sites where the given dates are out of season should be empty", availableSitesOutofRange1.size() == 0);
		assertTrue("List of Sites where the given dates are out of season should be empty", availableSitesOutofRange2.size() == 0);
		assertTrue("List of Sites where the given dates are out of season should be empty", availableSitesOutofRange3.size() == 0);
	}
	
}
