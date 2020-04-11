package com.techelevator.DAOTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.techelevator.model.Employee;
import com.techelevator.model.EmployeeJDBCDAO;
import com.techelevator.model.Field;
import com.techelevator.model.FieldJDBCDAO;
import com.techelevator.model.Skill;
import com.techelevator.model.SkillJDBCDAO;

public class EmployeeDAOTest {
	
	private static SingleConnectionDataSource dataSource;
	private EmployeeJDBCDAO employeeDAO;
	private AddressJDBCDAO addressDAO;
	private FieldJDBCDAO fieldDAO;
	private SkillJDBCDAO skillDAO;
	
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
		employeeDAO = new EmployeeJDBCDAO(dataSource);
		addressDAO = new AddressJDBCDAO(dataSource);
		fieldDAO = new FieldJDBCDAO(dataSource);
		skillDAO = new SkillJDBCDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void createEmployee_adds_employee_to_database() {
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		addressDAO.createAddress(address);
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT COUNT(*) FROM employees";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		int initialCount = 0;
		while (result.next()) {
			initialCount = result.getInt("count");
		}
		
		Employee employee = testEmployee("First", "Last", address.getId(), "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", "");
		assertNotNull(employee);
		employeeDAO.createEmployee(employee);
		
		result = jdbcTemplate.queryForRowSet(sql);
		int postCount = 0;
		while (result.next()) {
			postCount = result.getInt("count");
		}
		
		assertEquals(initialCount + 1, postCount);
	}
	
	private Field testField(String name, String type) {
		Field field = new Field();
		field.setName(name);
		field.setType(type);
		fieldDAO.createField(field);
		return field;
	}
	
	private Skill testSkill(String fieldId, int experience, String summary) {
		Skill skill = new Skill();
    	skill.setField(fieldId);
    	skill.setExperience(experience);
    	skill.setSummary(summary);
    	skillDAO.createSkill(skill);
    	return skill;
	}
	
	private Address testAddress(String street, String suite, String city, String region, String postal, String country) {
		Address address = new Address();
		address.setStreet(street);
		address.setSuite(suite);
		address.setCity(city);
		address.setRegion(region);
		address.setPostal(postal);
		address.setCountry(country);
		addressDAO.createAddress(address);
		return address;
	}
	
	private Employee testEmployee(String firstName, String lastName, String address, String contactEmail, 
			String companyEmail, String birthDate, String hiredDate, String role, String businessUnit, String assignedTo) {
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setAddress(address);
		employee.setContactEmail(contactEmail);
		employee.setCompanyEmail(companyEmail);
		employee.setBirthDate(birthDate);
		employee.setHiredDate(hiredDate);
		employee.setRole(role);
		employee.setBusinessUnit(businessUnit);
		employee.setAssignedTo(assignedTo);
		return employee;
	}

}
