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
import com.techelevator.model.EmpSkillJDBCDAO;
import com.techelevator.model.Employee;
import com.techelevator.model.EmployeeJDBCDAO;
import com.techelevator.model.Field;
import com.techelevator.model.FieldJDBCDAO;
import com.techelevator.model.Skill;
import com.techelevator.model.SkillJDBCDAO;

public class EmpSkillDAOTest {
	
	private static SingleConnectionDataSource dataSource;
	private EmployeeJDBCDAO employeeDAO;
	private FieldJDBCDAO fieldDAO;
	private SkillJDBCDAO skillDAO;
	private AddressJDBCDAO addressDAO;
	private EmpSkillJDBCDAO empskillDAO;
	
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
		fieldDAO = new FieldJDBCDAO(dataSource);
		skillDAO = new SkillJDBCDAO(dataSource);
		addressDAO = new AddressJDBCDAO(dataSource);
		empskillDAO = new EmpSkillJDBCDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void getAllSkillsByEmployeeId_returns_skills_of_employee() {
		Field field = testField("Field newname", "Field newtype");
		Skill skill = testSkill(field.getId(), 24, "Skill summary");
		Address address = testAddress("Street", "Suite", "City", "RGN", "Postal", "US");
		Employee employee = testEmployee("John", "Doe", address.getId(), "ContactEmail@place.com", 
				"CompanyEmail@company.com", "YYYY-MM-DD", "YYYY-MM-DD", "Role", "Business Unit", "Assigned To");
		
		List<Skill> skillList = empskillDAO.getAllSkillsByEmployeeId(employee.getId());
		int initialCount = skillList.size();
		
		empskillDAO.addSkillToEmployee(employee.getId(), skill.getId());
		skillList = empskillDAO.getAllSkillsByEmployeeId(employee.getId());
		int postCount = skillList.size();
		
		assertEquals(initialCount + 1, postCount);
	}

	@Test
	public void addSkillToEmployee_adds_skill_id_to_employee_id() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Field field = testField("Generic name", "Generic type");
		Skill skill = testSkill(field.getId(), 24, "Skill summary");
		Address address = testAddress("Street", "Suite", "City", "RGN", "Postal", "US");
		Employee employee = testEmployee("John", "Doe", address.getId(), "ContactEmail@place.com", 
				"CompanyEmail@company.com", "YYYY-MM-DD", "YYYY-MM-DD", "Role", "Business Unit", "Assigned To");
		
		String sql = "SELECT COUNT(*) FROM employee_skills WHERE employee = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, employee.getId());
		int initialCount = 0;
		while (result.next()) {
			initialCount = result.getInt("count");
		}
		
		empskillDAO.addSkillToEmployee(employee.getId(), skill.getId());
		result = jdbcTemplate.queryForRowSet(sql, employee.getId());
		int postCount = 0;
		while (result.next()) {
			postCount = result.getInt("count");
		}
		
		assertEquals(initialCount + 1, postCount);
	}
	
	@Test
	public void updateSkillFromEmployeeById_updates_skill_under_employee() {
		Field field = testField("Generic name", "Generic type");
		Skill skill = testSkill(field.getId(), 24, "Skill summary");
		Address address = testAddress("Street", "Suite", "City", "RGN", "Postal", "US");
		Employee employee = testEmployee("John", "Doe", address.getId(), "ContactEmail@place.com", 
				"CompanyEmail@company.com", "YYYY-MM-DD", "YYYY-MM-DD", "Role", "Business Unit", "Assigned To");
		
		empskillDAO.addSkillToEmployee(employee.getId(), skill.getId());
		
		Field newField = testField("It's a new name", "It's a new type");
		
		Skill updatedSkill = new Skill();
		updatedSkill.setField(newField.getId());
		updatedSkill.setExperience(60);
		updatedSkill.setSummary("Nothing to see here");
		
		empskillDAO.updateSkillFromEmployeeById(employee.getId(), skill.getId(), updatedSkill);
		Skill returnedSkill = skillDAO.getSkillById(skill.getId());
		assertSkillsAreEqual(returnedSkill, updatedSkill);
	}
	
	private Field testField(String name, String type) {
		Field field = new Field();
		field.setId(fieldDAO.generateUUID());
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
		employeeDAO.createEmployee(employee);
		return employee;
	}
	
	private void assertSkillsAreEqual(Skill skill1, Skill skill2) {
		assertNotNull(skill1);
		assertNotNull(skill2);
		assertTrue(skill1.getId().equals(skill2.getId()));
		assertTrue(skill1.getField().equals(skill2.getField()));
		assertTrue(skill1.getExperience() == skill2.getExperience());
		assertTrue(skill1.getSummary().equals(skill2.getSummary()));
	}

}
