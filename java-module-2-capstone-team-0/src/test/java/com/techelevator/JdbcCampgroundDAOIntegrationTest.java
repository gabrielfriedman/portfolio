package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import com.techelevator.model.campground.Campground;
import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.park.Park;

public class JdbcCampgroundDAOIntegrationTest extends DAOIntegrationTest {

	private DataSource dataSource = getDataSource();
	private JdbcCampgroundDAO testCampground;
	private JdbcParkDAO testPark;
	private Park thisPark;
	
	@Before
	public void setup() {
		String sqlInsertCampground = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) " + 
									"VALUES (1000, 1, 'Garbage Island', 05, 07, 5.00)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertCampground);
		testCampground = new JdbcCampgroundDAO(dataSource);
		testPark = new JdbcParkDAO(dataSource);
		thisPark = testPark.selectPark(1);
	}
	
	@Test
	public void test_select_campground() {
		Campground garbageIsland = testCampground.selectCampground((long) 1000);
		assertEquals("Method should create Garbage Island campground", "Garbage Island", garbageIsland.getName());
	}
	
	
	@Test
	public void View_All_Campgrounds_Test() {
		List<Campground> allCampgrounds = testCampground.viewAllCampgrounds(thisPark);
		assertFalse("List of all campgrounds should not be empty", allCampgrounds.isEmpty());
		assertEquals("List of all campgrounds should contain Garbage Island", "Garbage Island", allCampgrounds.get(allCampgrounds.size() - 1).getName());
	}

	
	@Test
	public void View_Available_Campgrounds_Test() {
		List<Campground> availableCampgrounds = testCampground.viewAvailableCampgrounds(thisPark);
		assertFalse("List of all campgrounds should not be empty", availableCampgrounds.isEmpty());
		assertNotEquals("List of all campgrounds should contain Garbage Island", "Garbage Island", availableCampgrounds.get(availableCampgrounds.size() - 1).getName());
	}
}



