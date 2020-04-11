package com.techelevator.model;

public interface SkillDAO {
	
	public void createSkill(Skill skill);
	public Skill getSkillById(String skillId);
	public void deleteSkillById(String skillId);

}
