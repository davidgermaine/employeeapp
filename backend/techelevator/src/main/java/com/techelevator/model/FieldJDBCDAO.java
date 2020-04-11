package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class FieldJDBCDAO implements FieldDAO {
	
	private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FieldJDBCDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    private Field mapRowToField(SqlRowSet result) {
    	Field field = new Field();
    	field.setId(result.getString("id"));
    	field.setName(result.getString("name"));
    	field.setType(result.getString("type"));
    	return field;
    }
    
    public String generateUUID() {
    	UUID uuid = UUID.randomUUID();
    	return "" + uuid;
    }

	@Override
	public void createField(Field field) {
		String sql = "INSERT INTO fields (id, name, type) VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, field.getId(), field.getName(), field.getType());
	}
	
	@Override
    public Field getFieldById(String fieldId) {
    	String sql = "SELECT * FROM fields WHERE id = ?";
    	SqlRowSet result = jdbcTemplate.queryForRowSet(sql, fieldId);
    	Field field = null;
    	while (result.next()) {
    		field = mapRowToField(result);    		
    	}
    	return field;
    }

	@Override
	public List<Field> getAllFields() {
		List<Field> fieldList = new ArrayList<>();
		String sql = "SELECT * FROM fields";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			Field field = mapRowToField(result);
			fieldList.add(field);
		}
		return fieldList;
	}

//	@Override
//	public Field getFieldBySkillId(String skillId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public void deleteFieldById(String fieldId) {
		String sql = "DELETE FROM fields WHERE id = ?";
		jdbcTemplate.update(sql, fieldId);
	}

}
