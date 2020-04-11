package com.techelevator.model;

import java.util.List;

public interface FieldDAO {
	
	public void createField(Field field);
	public List<Field> getAllFields();
	public Field getFieldBySkillId(String skillId);
	public void deleteFieldById(String fieldId);

}
