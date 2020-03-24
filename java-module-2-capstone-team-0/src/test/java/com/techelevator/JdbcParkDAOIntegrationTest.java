package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;


import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.park.Park;

public class JdbcParkDAOIntegrationTest extends DAOIntegrationTest {

	private DataSource dataSource = getDataSource();
	private JdbcParkDAO testPark;
	
	@Before
	public void setup() {
		String sqlInsertPark = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) " + 
									"VALUES (1000, 'Trash Heap National Park','New Jersey','1776-07-04',1500,5, " + 
									"'Come visit the sacred spot where all our nations trash is heaped into a beautiful, smelly mountain.  Perfect spot for those who love climbing and garbage picking')";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertPark);
		testPark = new JdbcParkDAO(dataSource);
	}
	
	@Test
	public void test_select_park() {
		Park trashHeapPark = testPark.selectPark(1000);
		assertEquals("Method should create Trash Heap National Park", "Trash Heap National Park", trashHeapPark.getName());
	}
	
	@Test
	public void test_view_all_parks() {
		List<Park> allParks = testPark.viewAllParks();
		assertFalse("List of all parks should not be empty", allParks.isEmpty());
		assertEquals("List of all parks should contain Trash Heap National Park", "Trash Heap National Park", allParks.get(allParks.size() - 1).getName());
	}
		
}
	

