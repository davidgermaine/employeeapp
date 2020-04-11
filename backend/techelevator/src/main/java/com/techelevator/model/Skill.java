package com.techelevator.model;

public class Skill {
	
	private String id;
	private Field field;
	private int experience;
	private String summary;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	

}
