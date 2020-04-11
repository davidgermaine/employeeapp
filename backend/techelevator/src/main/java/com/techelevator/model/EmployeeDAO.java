package com.techelevator.model;

import java.util.List;

public interface EmployeeDAO {
	
	public List<Employee> getAllEmployees();
	public void createEmployee(Employee employee);
	public Employee getEmployeeById(String employeeId);
	public void updateEmployeeById(String employeeId, Employee updatedEmployee);
	public void deleteEmployeeById(String employeeId);
	
	public List<Employee> getEmployeesByRole(String employeeRole);

}
