package com.techelevator.model;

import java.util.List;

public interface SkillDAO {
	
	public void createSkill(Skill skill);
	public List<Skill> getAllSkills();
	public void deleteSkillById(String skillId);

}
