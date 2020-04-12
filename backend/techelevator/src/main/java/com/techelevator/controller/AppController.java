package com.techelevator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}