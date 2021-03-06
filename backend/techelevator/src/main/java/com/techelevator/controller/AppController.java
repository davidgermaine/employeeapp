package com.techelevator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.Address;
import com.techelevator.model.AddressDAO;
import com.techelevator.model.Employee;
import com.techelevator.model.EmployeeDAO;
import com.techelevator.model.Field;
import com.techelevator.model.FieldDAO;
import com.techelevator.model.Skill;
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
	
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployeeFromId(@PathVariable String employeeId) {
		Employee employee = employeeDAO.getEmployeeById(employeeId);
		return employee;
	}
	
	@PutMapping("/employees/{employeeId}")
	@ResponseStatus(HttpStatus.OK)
	public void updateEmployeeFromId(@RequestBody Employee employee, @PathVariable String employeeId) {
		employeeDAO.updateEmployeeById(employeeId, employee);
	}
	
	@DeleteMapping("/employees/{employeeId}")
	@ResponseStatus(HttpStatus.OK)
	public void removeEmployeeFromId(@PathVariable String employeeId) {
		String addressId = employeeDAO.getEmployeeById(employeeId).getAddress().getId();
		employeeDAO.deleteEmployeeById(employeeId);
		addressDAO.deleteAddressById(addressId);
	}
	
	
	
	
	
	@GetMapping("/employees/{employeeId}/skills")
	public List<Skill> getSkillsFromEmployeeId(@PathVariable String employeeId) {
		List<Skill> skillList = employeeDAO.getAllSkillsByEmployeeId(employeeId);
		return skillList;
	}
	
	@PostMapping("/employees/{employeeId}/skills")
	@ResponseStatus(HttpStatus.CREATED)
	public void addSkillToEmployee(@RequestBody Skill skill, @PathVariable String employeeId) {
		Field field = skill.getField();
		fieldDAO.createField(field);
		skill.getField().setId(field.getId());
		skillDAO.createSkill(skill);
		employeeDAO.addSkillToEmployee(employeeId, skill.getId());
	}
	
	@GetMapping("/employees/{employeeId}/skills/{skillId}")
	public Skill getSkillFromEmployeeIdAndSkillId(@PathVariable String employeeId, @PathVariable String skillId) {
		List<Skill> skillList = employeeDAO.getAllSkillsByEmployeeId(employeeId);
		Skill skill = skillDAO.getSkillById(skillId);
		for (Skill skillItem : skillList) {
			if (skill.getId().equals(skillItem.getId())) {
				return skill;
			}
		}
		return null;
	}
	
	@PutMapping("/employees/{employeeId}/skills/{skillId}")
	@ResponseStatus(HttpStatus.OK)
	public void updateSkillWithEmployeeAndSkillId(@RequestBody Skill skill, @PathVariable String employeeId, @PathVariable String skillId) {
		Field field = skill.getField();
		fieldDAO.updateFieldById(field.getId(), field);
		skillDAO.updateSkillById(skillId, skill);
	}
	
	@DeleteMapping("/employees/{employeeId}/skills/{skillId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteEmployeeSkillWithId(@PathVariable String employeeId, @PathVariable String skillId) {
		employeeDAO.deleteSkillFromEmployeeById(employeeId, skillId);
	}

}