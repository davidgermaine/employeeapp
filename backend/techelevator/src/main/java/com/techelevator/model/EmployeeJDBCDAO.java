package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class EmployeeJDBCDAO implements EmployeeDAO {
	
	private final JdbcTemplate jdbcTemplate;
	private AddressJDBCDAO addressDAO;
	private FieldJDBCDAO fieldDAO;

    @Autowired
    public EmployeeJDBCDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.addressDAO = new AddressJDBCDAO(dataSource);
        this.fieldDAO = new FieldJDBCDAO(dataSource);
    }
    
    private Employee mapRowToEmployee(SqlRowSet result) {
    	Employee employee = new Employee();
    	employee.setId(result.getString("id"));
    	employee.setFirstName(result.getString("firstname"));
    	employee.setLastName(result.getString("lastname"));
    	employee.setAddress(addressDAO.getAddressById(result.getString("address")));
    	employee.setContactEmail(result.getString("contactemail"));
    	employee.setCompanyEmail(result.getString("companyemail"));
    	employee.setBirthDate(result.getString("birthdate"));
    	employee.setHiredDate(result.getString("hireddate"));
    	employee.setRole(result.getString("role"));
    	employee.setBusinessUnit(result.getString("businessunit"));
    	employee.setSkills(getAllSkillsByEmployeeId(result.getString("id")));
    	employee.setAssignedTo(result.getString("assignedto"));
    	return employee;
    }
    
    private Skill mapRowToSkill(SqlRowSet result) {
    	Skill skill = new Skill();
    	skill.setId(result.getString("id"));
    	skill.setField(fieldDAO.getFieldById(result.getString("field")));
    	skill.setExperience(result.getInt("experience"));
    	skill.setSummary(result.getString("summary"));
    	return skill;
    }
    
    public String generateUUID() {
    	UUID uuid = UUID.randomUUID();
    	return "" + uuid;
    }

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employeeList = new ArrayList<>();
		String sql = "SELECT * FROM employees";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			Employee employee = mapRowToEmployee(result);
			employeeList.add(employee);
		}
		return employeeList;
	}

	@Override
	public void createEmployee(Employee employee) {
		String sql = "INSERT INTO employees (id, firstname, lastname, address, contactemail, companyemail, "
				+ "birthdate, hireddate, role, businessunit, assignedto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		employee.setId(generateUUID());
		jdbcTemplate.update(sql, employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getAddress().getId(), 
				employee.getContactEmail(), employee.getCompanyEmail(), employee.getBirthDate(), employee.getHiredDate(), 
				employee.getRole(), employee.getBusinessUnit(), employee.getAssignedTo());
//		for (Skill skill : employee.getSkills()) {
//			addSkillToEmployee(employee.getId(), skill.getId());
//		}
	}

	@Override
	public Employee getEmployeeById(String employeeId) {
		String sql = "SELECT * FROM employees WHERE id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, employeeId);
		Employee employee = new Employee();
		while (result.next()) {
			employee = mapRowToEmployee(result);
		}
		employee.setSkills(getAllSkillsByEmployeeId(employee.getId()));
		return employee;
	}

	@Override
	public void updateEmployeeById(String employeeId, Employee updatedEmployee) {
		String sql = "UPDATE employees SET firstname = ?, lastname = ?, address = ?, contactemail = ?, "
				+ "companyemail = ?, birthdate = ?, hireddate = ?, role = ?, businessunit = ?, assignedto = ? "
				+ "WHERE id = ?";
		jdbcTemplate.update(sql, updatedEmployee.getFirstName(), updatedEmployee.getLastName(), updatedEmployee.getAddress().getId(), 
				updatedEmployee.getContactEmail(), updatedEmployee.getCompanyEmail(), updatedEmployee.getBirthDate(), 
				updatedEmployee.getHiredDate(), updatedEmployee.getRole(), updatedEmployee.getBusinessUnit(), 
				updatedEmployee.getAssignedTo(), employeeId);
	}

	@Override
	public void deleteEmployeeById(String employeeId) {
		String sql = "DELETE FROM employee_skills WHERE employee = ?";
		jdbcTemplate.update(sql, employeeId);
		String sql2 = "DELETE FROM employees WHERE id = ?";
		jdbcTemplate.update(sql2, employeeId);
	}

	@Override
	public List<Employee> getEmployeesByRole(String employeeRole) {
		List<Employee> employeeList = new ArrayList<>();
		String sql = "SELECT * FROM employees WHERE role = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, employeeRole);
		while (result.next()) {
			Employee employee = mapRowToEmployee(result);
			employeeList.add(employee);
		}
		return employeeList;
	}
	
	
	
	
	
	@Override
	public List<Skill> getAllSkillsByEmployeeId(String employeeId) {
		List<Skill> skillList = new ArrayList<>();
		String sql = "SELECT skills.* FROM skills "
				+ "JOIN employee_skills ON skills.id = employee_skills.skill "
				+ "WHERE employee_skills.employee = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, employeeId);
		while (result.next()) {
			Skill skill = mapRowToSkill(result);
			skillList.add(skill);
		}
		return skillList;
	}

	@Override
	public void addSkillToEmployee(String employeeId, String skillId) {
		String sql = "INSERT INTO employee_skills (employee, skill) VALUES (?, ?)";
		jdbcTemplate.update(sql, employeeId, skillId);
	}

	@Override
	public Skill getSkillFromEmployeeById(String employeeId, String skillId) {
		String sql = "SELECT skills.* FROM skills "
				+ "JOIN employee_skills ON skills.id = employee_skills.skill "
				+ "WHERE employee_skills.employee = ? AND employee_skills.skill = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, employeeId, skillId);
		Skill skill = null;
		while (result.next()) {
			skill = mapRowToSkill(result);
		}
		return skill;
	}

	@Override
	public void updateSkillFromEmployeeById(String employeeId, String skillId, Skill updatedSkill) {
		String sql = "UPDATE skills SET field = ?, experience = ?, summary = ? "
				+ "FROM employee_skills "
				+ "WHERE employee_skills.employee = ? AND employee_skills.skill = ?";
		updatedSkill.setId(skillId);
		jdbcTemplate.update(sql, updatedSkill.getField().getId(), updatedSkill.getExperience(), updatedSkill.getSummary(), 
				employeeId, skillId);
	}

	@Override
	public void deleteSkillFromEmployeeById(String employeeId, String skillId) {
		String sql = "DELETE FROM employee_skills WHERE employee = ? AND skill = ?";
		jdbcTemplate.update(sql, employeeId, skillId);
	}
	
	
	
	

}
