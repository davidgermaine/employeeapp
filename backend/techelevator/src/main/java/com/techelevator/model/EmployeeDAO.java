package com.techelevator.model;

import java.util.List;

public interface EmployeeDAO {
	
	public List<Employee> getAllEmployees();
	public void createEmployee(Employee employee);
	public Employee getEmployeeById(String employeeId);
	public void updateEmployeeById(String employeeId, Employee updatedEmployee);
	public void deleteEmployeeById(String employeeId);
	
	public List<Skill> getAllSkillsByEmployeeId(String employeeId);
	public void addSkillToEmployee(String employeeId, Skill newSkill);
	public Skill getSkillFromEmployee(String employeeId, String skillId);
	public void updateSkillFromEmployeeById(String employeeId, String skillId, Skill updatedSkill);
	public void deleteSkillFromEmployeeById(String employeeId, String skillId);
	
	public List<Employee> getEmployeesByRole(String employeeRole);

}
