package com.techelevator.model;

import java.util.List;

public interface EmpSkillDAO {
	
	public List<Skill> getAllSkillsByEmployeeId(String employeeId);
	public void addSkillToEmployee(String employeeId, Skill newSkill);
	public Skill getSkillFromEmployee(String employeeId, String skillId);
	public void updateSkillFromEmployeeById(String employeeId, String skillId, Skill updatedSkill);
	public void deleteSkillFromEmployeeById(String employeeId, String skillId);

}
