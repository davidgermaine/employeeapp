package com.techelevator.DAOTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Address;
import com.techelevator.model.AddressJDBCDAO;

public class AddressDAOTest {
	
	private static SingleConnectionDataSource dataSource;
	private AddressJDBCDAO addressDAO;
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/employeeapp");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
		addressDAO = new AddressJDBCDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void createAddress_adds_address_to_database() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT COUNT(*) FROM addresses";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		int initialCount = 0;
		while (result.next()) {
			initialCount = result.getInt("count");
		}
		
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		addressDAO.createAddress(address);
		assertNotNull(address);
		
		result = jdbcTemplate.queryForRowSet(sql);
		int postCount = 0;
		while (result.next()) {
			postCount = result.getInt("count");
		}
		
		assertEquals(initialCount + 1, postCount);
	}
	
	private Address testAddress(String street, String suite, String city, String region, String postal, String country) {
		Address address = new Address();
		address.setStreet(street);
		address.setSuite(suite);
		address.setCity(city);
		address.setRegion(region);
		address.setPostal(postal);
		address.setCountry(country);
		return address;
	}
	
	private void assertAddressesAreEqual(Address address1, Address address2) {
		assertNotNull(address1);
		assertNotNull(address2);
		assertTrue(address1.getId().equals(address2.getId()));
		assertTrue(address1.getStreet().equals(address2.getStreet()));
		assertTrue(address1.getSuite().equals(address2.getSuite()));
		assertTrue(address1.getCity().equals(address2.getCity()));
		assertTrue(address1.getRegion().equals(address2.getRegion()));
		assertTrue(address1.getPostal().equals(address2.getPostal()));
		assertTrue(address1.getCountry().equals(address2.getCountry()));
	}

}
