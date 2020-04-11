package com.techelevator.model;

public interface SkillDAO {
	
	public void createSkill(Skill skill);
	public Skill getSkillById(String skillId);
	public void updateSkillById(String skillId, Skill skill);
	public void deleteSkillById(String skillId);

}
