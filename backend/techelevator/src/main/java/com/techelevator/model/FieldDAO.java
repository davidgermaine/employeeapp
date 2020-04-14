package com.techelevator.model;

import java.util.List;

public interface FieldDAO {
	
	public void createField(Field field);
	public Field getFieldById(String fieldId);
	public List<Field> getAllFields();
	public void updateFieldById(String fieldId, Field field);
	public void deleteFieldById(String fieldId);

}
