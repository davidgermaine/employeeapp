package com.techelevator.DAOTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

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

public class EmployeeDAOTest {
	
	private static SingleConnectionDataSource dataSource;
	private EmployeeJDBCDAO employeeDAO;
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
		employeeDAO = new EmployeeJDBCDAO(dataSource);
		addressDAO = new AddressJDBCDAO(dataSource);
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
	
	@Test
	public void getAllEmployees_returns_all_employees() {
		List<Employee> employeeList = employeeDAO.getAllEmployees();
		int initialCount = employeeList.size();
		
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		addressDAO.createAddress(address);
		Employee employee = testEmployee("First", "Last", address.getId(), "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", "");
		employeeDAO.createEmployee(employee);
		
		employeeList = employeeDAO.getAllEmployees();
		int postCount = employeeList.size();
		
		assertEquals(initialCount + 1, postCount);
	}
	
	@Test
	public void getEmployeeById_returns_proper_employee() {
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		addressDAO.createAddress(address);
		Employee employee = testEmployee("First", "Last", address.getId(), "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", "");
		employeeDAO.createEmployee(employee);
		
		Employee returnedEmployee = employeeDAO.getEmployeeById(employee.getId());
		assertEmployeesAreEqual(employee, returnedEmployee);
	}
	
	@Test
	public void updateEmployeeById_updates_proper_employee() {
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		addressDAO.createAddress(address);
		Address address2 = testAddress("Test Highway", "Test Condo", "Test Village", "MI", "48072", "US");
		addressDAO.createAddress(address2);
		Employee employee = testEmployee("First", "Last", address.getId(), "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", "");
		employeeDAO.createEmployee(employee);
		
		Employee updatedEmployee = testEmployee("NewFirst", "NewLast", address2.getId(), "new@org.net", "new@company.com", 
				"1993-06-06", "2019-10-14", "New Role", "New Unit", "ManagerId goes here");
		updatedEmployee.setId(employee.getId());
		employeeDAO.updateEmployeeById(employee.getId(), updatedEmployee);
		
		assertEmployeesAreEqual(updatedEmployee, employeeDAO.getEmployeeById(employee.getId()));
	}
	
	@Test
	public void deleteEmployeeById_removes_employee_from_database() {
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		addressDAO.createAddress(address);
		Employee employee = testEmployee("First", "Last", address.getId(), "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", "");
		employeeDAO.createEmployee(employee);
		
		List<Employee> employeeList = employeeDAO.getAllEmployees();
		int initialCount = employeeList.size();
		employeeDAO.deleteEmployeeById(employee.getId());
		employeeList = employeeDAO.getAllEmployees();
		int postCount = employeeList.size();
		
		assertEquals(initialCount - 1, postCount);
	}
	
	@Test
	public void getEmployeesByRole_retrieves_all_employees_with_role() {
		List<Employee> employeeList = employeeDAO.getEmployeesByRole("Test Role");
		int initialCount = employeeList.size();
		
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		addressDAO.createAddress(address);
		Employee employee = testEmployee("First", "Last", address.getId(), "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", "");
		employeeDAO.createEmployee(employee);
		
		employeeList = employeeDAO.getEmployeesByRole("Test Role");
		assertNotNull(employeeList);
		int postCount = employeeList.size();
		
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
	
	private void assertEmployeesAreEqual(Employee employee1, Employee employee2) {
		assertNotNull(employee1);
		assertNotNull(employee2);
		assertTrue(employee1.getId().equals(employee2.getId()));
		assertTrue(employee1.getFirstName().equals(employee2.getFirstName()));
		assertTrue(employee1.getLastName().equals(employee2.getLastName()));
		assertTrue(employee1.getAddress().equals(employee2.getAddress()));
		assertTrue(employee1.getContactEmail().equals(employee2.getContactEmail()));
		assertTrue(employee1.getCompanyEmail().equals(employee2.getCompanyEmail()));
		assertTrue(employee1.getBirthDate().equals(employee2.getBirthDate()));
		assertTrue(employee1.getHiredDate().equals(employee2.getHiredDate()));
		assertTrue(employee1.getRole().equals(employee2.getRole()));
		assertTrue(employee1.getBusinessUnit().equals(employee2.getBusinessUnit()));
		assertTrue(employee1.getAssignedTo().equals(employee2.getAssignedTo()));
	}

}
