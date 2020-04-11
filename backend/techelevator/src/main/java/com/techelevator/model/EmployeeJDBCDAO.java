package com.techelevator.model;

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

    @Autowired
    public EmployeeJDBCDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    private Employee mapRowToEmployee(SqlRowSet result) {
    	Employee employee = new Employee();
    	employee.setId(result.getString("id"));
    	employee.setFirstName(result.getString("firstname"));
    	employee.setLastName(result.getString("lastname"));
    	employee.setAddress(result.getString("address"));
    	employee.setContactEmail(result.getString("contactemail"));
    	employee.setCompanyEmail(result.getString("companyemail"));
    	employee.setBirthDate(result.getString("birthdate"));
    	employee.setHiredDate(result.getString("hireddate"));
    	employee.setRole(result.getString("role"));
    	employee.setBusinessUnit(result.getString("businessunit"));
    	employee.setAssignedTo(result.getString("assignedto"));
    	return employee;
    }
    
    public String generateUUID() {
    	UUID uuid = UUID.randomUUID();
    	return "" + uuid;
    }

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createEmployee(Employee employee) {
		String sql = "INSERT INTO employees (id, firstname, lastname, address, contactemail, companyemail, "
				+ "birthdate, hireddate, role, businessunit, assignedto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		employee.setId(generateUUID());
		jdbcTemplate.update(sql, employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getAddress(), 
				employee.getContactEmail(), employee.getCompanyEmail(), employee.getBirthDate(), employee.getHiredDate(), 
				employee.getRole(), employee.getBusinessUnit(), employee.getAssignedTo());
	}

	@Override
	public Employee getEmployeeById(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateEmployeeById(String employeeId, Employee updatedEmployee) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteEmployeeById(String employeeId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Skill> getAllSkillsByEmployeeId(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSkillToEmployee(String employeeId, Skill newSkill) {
		// TODO Auto-generated method stub

	}

	@Override
	public Skill getSkillFromEmployee(String employeeId, String skillId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSkillFromEmployeeById(String employeeId, String skillId, Skill updatedSkill) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSkillFromEmployeeById(String employeeId, String skillId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Employee> getEmployeesByRole(String employeeRole) {
		// TODO Auto-generated method stub
		return null;
	}

}
