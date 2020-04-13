package com.techelevator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.Address;
import com.techelevator.model.AddressDAO;
import com.techelevator.model.Employee;
import com.techelevator.model.EmployeeDAO;
import com.techelevator.model.FieldDAO;
import com.techelevator.model.SkillDAO;

@RestController
@CrossOrigin
public class AppController {
	
	@Autowired
	private FieldDAO fieldDAO;
	
	@Autowired
	private SkillDAO skillDAO;
	
	@Autowired
	private AddressDAO addressDAO;
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
	    List<Employee> allEmployees = employeeDAO.getAllEmployees();
	    return allEmployees;
	}
	
	@GetMapping("/employees/supervisors")
	public List<Employee> getSupervisors() {
		List<Employee> managerList = employeeDAO.getEmployeesByRole("Project Manager");
		List<Employee> directorList = employeeDAO.getEmployeesByRole("Director");
		
		List<Employee> supervisorList = new ArrayList<>();
		for (Employee manager : managerList) {
			supervisorList.add(manager);
		}
		for (Employee director : directorList) {
			supervisorList.add(director);
		}
		return supervisorList;
	}
	
	@PostMapping("/employees")
	@ResponseStatus(HttpStatus.CREATED)
	public void addEmployeeToDatabase(@RequestBody Employee employee) {
		Address address = employee.getAddress();
		addressDAO.createAddress(address);
		employee.getAddress().setId(address.getId());
		employeeDAO.createEmployee(employee);
	}

}