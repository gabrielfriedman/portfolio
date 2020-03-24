package com.techelevator.model.site;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.campground.Campground;

public class JdbcSiteDAO implements SiteDAO {

	private JdbcTemplate myJdbcTemplate;

	public JdbcSiteDAO(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource);
	}

	@Override
	public List<Site> viewAllSitesByCampgroundID(Campground selectedCampground) {
		List<Site> siteList = new ArrayList<Site>();
		String sqlViewAllSites = "select * from site where campground_id = ? order by site_number";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sqlViewAllSites, selectedCampground.getCampgroundId());
		while (results.next()) {
			Site theSite = mapToSite(results);
			siteList.add(theSite);
		}
		return siteList;
	}

	@Override
	public List<Site> viewAllSitesByDateRange(Campground selectedCampground, LocalDate arrivalDate, LocalDate departureDate) {
		List<Site> availableSiteList = viewAllSitesByCampgroundID(selectedCampground);
		List<Long> conflictingReservations = new ArrayList<Long>();
		List<Site>topFiveSites = new ArrayList<Site>();
		String sqlReservedSites = "select * from reservation where site_id in (select site_id from site where campground_id = ?) and ((from_date <= ? and to_date >= ?) or (from_date < ? and to_date >= ?) or (to_date > ? and to_date <= ?))";
		SqlRowSet results = myJdbcTemplate.queryForRowSet(sqlReservedSites, selectedCampground.getCampgroundId(), arrivalDate, departureDate, departureDate, departureDate, arrivalDate, departureDate);
				
		while (results.next()) {
			conflictingReservations.add(results.getLong("site_id"));  //build a list of all reservations that conflict with the the given arrival/departure dates
		}
		
		for (int i = 0; i < conflictingReservations.size(); i++) {   //iterate through the list of conflicting reservations
			for(int j = 0; j < availableSiteList.size(); j++) {
				if (conflictingReservations.get(i) == availableSiteList.get(j).getSiteId()) {
					availableSiteList.remove(j);    //if a given site in the list of sites is reserved during the given time, it is removed from the list
				}
			}
		}
		
		
		if (selectedCampground.getOpenFromMonth() > arrivalDate.getMonthValue()   
			|| selectedCampground.getOpenToMonth() < arrivalDate.getMonthValue()
			|| selectedCampground.getOpenToMonth() < departureDate.getMonthValue()) {
				
			availableSiteList.removeAll(availableSiteList);   //if the given arrival/departure dates are out of bounds from the campgrounds open/close dates, all sites will be removed
		}
		
	
		if (availableSiteList.size() >= 5) {   //if the list of available sites is 5 or more, they will be sorted into a list of the first 5
			for (int i = 0; i < 5; i++) {
				topFiveSites.add(availableSiteList.get(i));
				
			}
			return topFiveSites;
		}
		else return availableSiteList;
		

		
	}

	private Site mapToSite(SqlRowSet results) {
		Site theSite = new Site();
		theSite.setSiteId(results.getLong("site_id"));
		theSite.setCampgroundId(results.getLong("campground_id"));
		theSite.setSiteNumber(results.getInt("site_number"));
		theSite.setMaxOccupancy(results.getInt("max_occupancy"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMaxRVLength(results.getInt("max_rv_length"));
		theSite.setUtilities(results.getBoolean("utilities"));
		return theSite;
	}

}
