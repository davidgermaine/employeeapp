package com.techelevator.DAOTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
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
import com.techelevator.model.Field;
import com.techelevator.model.FieldJDBCDAO;
import com.techelevator.model.Skill;
import com.techelevator.model.SkillJDBCDAO;

public class EmployeeDAOTest {
	
	private static SingleConnectionDataSource dataSource;
	private EmployeeJDBCDAO employeeDAO;
	private AddressJDBCDAO addressDAO;
	private SkillJDBCDAO skillDAO;
	private FieldJDBCDAO fieldDAO;
	
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
		skillDAO = new SkillJDBCDAO(dataSource);
		fieldDAO = new FieldJDBCDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void createEmployee_adds_employee_to_database() {
//		Field field = testField("Field newname", "Field newtype");
//		Skill skill = testSkill(field, 24, "Skill summary");
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT COUNT(*) FROM employees";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		int initialCount = 0;
		while (result.next()) {
			initialCount = result.getInt("count");
		}
		
		List<Skill> skillList = new ArrayList<>();
//		skillList.add(skill);
		
		Employee employee = testEmployee("First", "Last", address, "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", skillList, "");
		assertNotNull(employee);
		
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
		
		Field field = testField("Field newname", "Field newtype");
		Skill skill = testSkill(field, 24, "Skill summary");
		
		List<Skill> skillList = new ArrayList<>();
		skillList.add(skill);
		
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		Employee employee = testEmployee("First", "Last", address, "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", skillList, "");
		
		employeeList = employeeDAO.getAllEmployees();
		int postCount = employeeList.size();
		
		assertEquals(initialCount + 1, postCount);
	}
	
	@Test
	public void getEmployeeById_returns_proper_employee() {
//		Field field = testField("Field newname", "Field newtype");
//		Skill skill = testSkill(field, 24, "Skill summary");
		
		List<Skill> skillList = new ArrayList<>();
//		skillList.add(skill);
		
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		Employee employee = testEmployee("First", "Last", address, "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", skillList, "");
		
		Employee returnedEmployee = employeeDAO.getEmployeeById(employee.getId());
		assertEmployeesAreEqual(employee, returnedEmployee);
	}
	
	@Test
	public void updateEmployeeById_updates_proper_employee() {
//		Field field = testField("Field newname", "Field newtype");
//		Skill skill = testSkill(field, 24, "Skill summary");
		
		List<Skill> skillList = new ArrayList<>();
//		skillList.add(skill);
		
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		Address address2 = testAddress("Test Highway", "Test Condo", "Test Village", "MI", "48072", "US");
		Employee employee = testEmployee("First", "Last", address, "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", skillList, "");
		
		Employee updatedEmployee = testEmployee2("NewFirst", "NewLast", address2, "new@org.net", "new@company.com", 
				"1993-06-06", "2019-10-14", "New Role", "New Unit", skillList, "ManagerId goes here");
		updatedEmployee.setId(employee.getId());
		employeeDAO.updateEmployeeById(employee.getId(), updatedEmployee);
		
		assertEmployeesAreEqual(updatedEmployee, employeeDAO.getEmployeeById(employee.getId()));
	}
	
	@Test
	public void deleteEmployeeById_removes_employee_from_database() {
		Field field = testField("Field newname", "Field newtype");
		Skill skill = testSkill(field, 24, "Skill summary");
		
		List<Skill> skillList = new ArrayList<>();
		skillList.add(skill);
		
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		Employee employee = testEmployee("First", "Last", address, "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", skillList, "");
		
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
		
		Field field = testField("Field newname", "Field newtype");
		Skill skill = testSkill(field, 24, "Skill summary");
		
		List<Skill> skillList = new ArrayList<>();
		skillList.add(skill);
		
		Address address = testAddress("Test Street", "", "Test City", "MI", "48074", "US");
		addressDAO.createAddress(address);
		Employee employee = testEmployee("First", "Last", address, "test@site.com", "test@company.com", 
				"1996-01-01", "2020-06-01", "Test Role", "Test Unit", skillList, "");
		
		employeeList = employeeDAO.getEmployeesByRole("Test Role");
		assertNotNull(employeeList);
		int postCount = employeeList.size();
		
		assertEquals(initialCount + 1, postCount);
	}
	
	
	
	
	
	@Test
	public void getAllSkillsByEmployeeId_returns_skills_of_employee() {
		Field field = testField("Field newname", "Field newtype");
		Skill skill = testSkill(field, 24, "Skill summary");
		List<Skill> skills = new ArrayList<>();
		skills.add(skill);
		
		Address address = testAddress("Street", "Suite", "City", "RGN", "Postal", "US");
		Employee employee = testEmployee("John", "Doe", address, "ContactEmail@place.com", 
				"CompanyEmail@company.com", "YYYY-MM-DD", "YYYY-MM-DD", "Role", "Business Unit", 
				skills, "Assigned To");
		
		List<Skill> skillList = employeeDAO.getAllSkillsByEmployeeId(employee.getId());
		int initialCount = skillList.size();
		
		employeeDAO.addSkillToEmployee(employee.getId(), skill.getId());
		skillList = employeeDAO.getAllSkillsByEmployeeId(employee.getId());
		int postCount = skillList.size();
		
		assertEquals(initialCount + 1, postCount);
	}

	@Test
	public void addSkillToEmployee_adds_skill_id_to_employee_id() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Field field = testField("Generic name", "Generic type");
		Skill skill = testSkill(field, 24, "Skill summary");
		List<Skill> skillList = new ArrayList<>();
		skillList.add(skill);
		
		Address address = testAddress("Street", "Suite", "City", "RGN", "Postal", "US");
		Employee employee = testEmployee("John", "Doe", address, "ContactEmail@place.com", 
				"CompanyEmail@company.com", "YYYY-MM-DD", "YYYY-MM-DD", "Role", "Business Unit", 
				skillList, "Assigned To");
		
		String sql = "SELECT COUNT(*) FROM employee_skills WHERE employee = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, employee.getId());
		int initialCount = 0;
		while (result.next()) {
			initialCount = result.getInt("count");
		}
		
		employeeDAO.addSkillToEmployee(employee.getId(), skill.getId());
		result = jdbcTemplate.queryForRowSet(sql, employee.getId());
		int postCount = 0;
		while (result.next()) {
			postCount = result.getInt("count");
		}
		
		System.out.println(postCount);
		
		assertEquals(initialCount + 1, postCount);
	}
	
	@Test
	public void getSkillFromEmployeeById_returns_proper_skill() {
		Field field = testField("Generic name", "Generic type");
		Skill skill = testSkill(field, 24, "Skill summary");
		List<Skill> skillList = new ArrayList<>();
		skillList.add(skill);
		
		Address address = testAddress("Street", "Suite", "City", "RGN", "Postal", "US");
		Employee employee = testEmployee("John", "Doe", address, "ContactEmail@place.com", 
				"CompanyEmail@company.com", "YYYY-MM-DD", "YYYY-MM-DD", "Role", "Business Unit", 
				skillList, "Assigned To");
	
		employeeDAO.addSkillToEmployee(employee.getId(), skill.getId());
		Skill returnedSkill = employeeDAO.getSkillFromEmployeeById(employee.getId(), skill.getId());
		assertSkillsAreEqual(skill, returnedSkill);
	}
	
	@Test
	public void updateSkillFromEmployeeById_updates_skill_under_employee() {
		Field field = testField("Generic name", "Generic type");
		Skill skill = testSkill(field, 24, "Skill summary");
		List<Skill> skillList = new ArrayList<>();
		skillList.add(skill);
		
		Address address = testAddress("Street", "Suite", "City", "RGN", "Postal", "US");
		Employee employee = testEmployee("John", "Doe", address, "ContactEmail@place.com", 
				"CompanyEmail@company.com", "YYYY-MM-DD", "YYYY-MM-DD", "Role", "Business Unit",
				skillList, "Assigned To");
		
		employeeDAO.addSkillToEmployee(employee.getId(), skill.getId());
		
		Field newField = testField("It's a new name", "It's a new type");
		
		Skill updatedSkill = new Skill();
		updatedSkill.setField(newField);
		updatedSkill.setExperience(60);
		updatedSkill.setSummary("Nothing to see here");
		
		employeeDAO.updateSkillFromEmployeeById(employee.getId(), skill.getId(), updatedSkill);
		Skill returnedSkill = skillDAO.getSkillById(skill.getId());
		assertSkillsAreEqual(returnedSkill, updatedSkill);
	}
	
	@Test
	public void deleteSkillFromEmployeeById_removes_skill_from_employee() {
		Field field = testField("Generic name", "Generic type");
		Skill skill = testSkill(field, 24, "Skill summary");
		List<Skill> skills = new ArrayList<>();
		
		Address address = testAddress("Street", "Suite", "City", "RGN", "Postal", "US");
		Employee employee = testEmployee("John", "Doe", address, "ContactEmail@place.com", 
				"CompanyEmail@company.com", "YYYY-MM-DD", "YYYY-MM-DD", "Role", "Business Unit", 
				skills, "Assigned To");
		
		employeeDAO.addSkillToEmployee(employee.getId(), skill.getId());
		List<Skill> skillList = employeeDAO.getAllSkillsByEmployeeId(employee.getId());
		int initialCount = skillList.size();
		
		employeeDAO.deleteSkillFromEmployeeById(employee.getId(), skill.getId());
		skillList = employeeDAO.getAllSkillsByEmployeeId(employee.getId());
		int postCount = skillList.size();
		
		assertEquals(initialCount - 1, postCount);
		assertNull(employeeDAO.getSkillFromEmployeeById(employee.getId(), skill.getId()));
	}
	
	
	
	
	
	private Field testField(String name, String type) {
		Field field = new Field();
		field.setId(fieldDAO.generateUUID());
		field.setName(name);
		field.setType(type);
		fieldDAO.createField(field);
		return field;
	}
	
	private Skill testSkill(Field field, int experience, String summary) {
		Skill skill = new Skill();
    	skill.setField(field);
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
	
	private Employee testEmployee(String firstName, String lastName, Address address, String contactEmail, 
			String companyEmail, String birthDate, String hiredDate, String role, String businessUnit, List<Skill> skills, String assignedTo) {
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
		employee.setSkills(skills);
		employee.setAssignedTo(assignedTo);
		employeeDAO.createEmployee(employee);
		return employee;
	}
	
	private Employee testEmployee2(String firstName, String lastName, Address address, String contactEmail, 
			String companyEmail, String birthDate, String hiredDate, String role, String businessUnit, List<Skill> skills, String assignedTo) {
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
		employee.setSkills(skills);
		employee.setAssignedTo(assignedTo);
		return employee;
	}
	
	private void assertEmployeesAreEqual(Employee employee1, Employee employee2) {
		assertNotNull(employee1);
		assertNotNull(employee2);
		assertTrue(employee1.getId().equals(employee2.getId()));
		assertTrue(employee1.getFirstName().equals(employee2.getFirstName()));
		assertTrue(employee1.getLastName().equals(employee2.getLastName()));
		
		assertAddressesAreEqual(employee1.getAddress(), employee2.getAddress());
		
		assertTrue(employee1.getContactEmail().equals(employee2.getContactEmail()));
		assertTrue(employee1.getCompanyEmail().equals(employee2.getCompanyEmail()));
		assertTrue(employee1.getBirthDate().equals(employee2.getBirthDate()));
		assertTrue(employee1.getHiredDate().equals(employee2.getHiredDate()));
		assertTrue(employee1.getRole().equals(employee2.getRole()));
		assertTrue(employee1.getBusinessUnit().equals(employee2.getBusinessUnit()));
		
		for (int i = 0; i < employee1.getSkills().size(); i++) {
			assertSkillsAreEqual(employee1.getSkills().get(i), employee2.getSkills().get(i));
		}
		
		assertTrue(employee1.getAssignedTo().equals(employee2.getAssignedTo()));
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
	
	private void assertSkillsAreEqual(Skill skill1, Skill skill2) {
		assertNotNull(skill1);
		assertNotNull(skill2);
		assertTrue(skill1.getId().equals(skill2.getId()));
		
		assertFieldsAreEqual(skill1.getField(), skill2.getField());
		
		assertTrue(skill1.getExperience() == skill2.getExperience());
		assertTrue(skill1.getSummary().equals(skill2.getSummary()));
	}
	
	private void assertFieldsAreEqual(Field field1, Field field2) {
		assertNotNull(field1);
		assertNotNull(field2);
		assertTrue(field1.getId().equals(field2.getId()));
		assertTrue(field1.getName().equals(field2.getName()));
		assertTrue(field1.getType().equals(field2.getType()));
	}

}
