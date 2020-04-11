package com.techelevator.DAOTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Field;
import com.techelevator.model.FieldJDBCDAO;

public class FieldDAOTest {
	
	private static SingleConnectionDataSource dataSource;
	private FieldJDBCDAO fieldDAO;
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/employeeapp");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
		fieldDAO = new FieldJDBCDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void createField_adds_field_to_database() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		int initialCount = 0;
		String sql = "SELECT COUNT(*) FROM fields";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			initialCount = result.getInt("count");
		}
		
		Field field = testField("Test Field", "Test Type");
		fieldDAO.createField(field);
		int postCount = 0;
		result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			postCount = result.getInt("count");
		}
		
		assertEquals(initialCount + 1, postCount);
		
		String fieldSql = "SELECT * FROM fields WHERE id = ?";
		result = jdbcTemplate.queryForRowSet(fieldSql, field.getId());
		Field resultField = new Field();
		while (result.next()) {
			resultField.setId(result.getString("id"));
			resultField.setName(result.getString("name"));
			resultField.setType(result.getString("type"));
		}
		assertFieldsAreEqual(field, resultField);
	}
	
	@Test
	public void getFieldById_retrieves_proper_field() {
		Field field1 = testField("Name 1", "Type 1");
		Field field2 = testField("Name 2", "Type 2");
		fieldDAO.createField(field1);
		fieldDAO.createField(field2);
		
		Field field1Return = fieldDAO.getFieldById(field1.getId());
		Field field2Return = fieldDAO.getFieldById(field2.getId());
		assertFieldsAreEqual(field1, field1Return);
		assertFieldsAreEqual(field2, field2Return);
	}
	
	@Test
	public void getAllFields_returns_all_fields_in_database() {
		int initialCount = fieldDAO.getAllFields().size();
		assertNotNull(fieldDAO.getAllFields());
		
		Field field1 = testField("Name 1", "Type 1");
		Field field2 = testField("Name 2", "Type 2");
		Field field3 = testField("Name 3", "Type 3");
		Field field4 = testField("Name 4", "Type 4");
		fieldDAO.createField(field1);
		fieldDAO.createField(field2);
		fieldDAO.createField(field3);
		fieldDAO.createField(field4);
		
		int postCount = fieldDAO.getAllFields().size();
		assertNotNull(fieldDAO.getAllFields());
		assertEquals(initialCount + 4, postCount);
	}
	
	@Test
	public void deleteFieldById_deletes_field_from_database() {
		Field field1 = testField("Name 1", "Type 1");
		Field field2 = testField("Name 2", "Type 2");
		Field field3 = testField("Name 3", "Type 3");
		Field field4 = testField("Name 4", "Type 4");
		fieldDAO.createField(field1);
		fieldDAO.createField(field2);
		fieldDAO.createField(field3);
		fieldDAO.createField(field4);
		
		int initialCount = fieldDAO.getAllFields().size();
		fieldDAO.deleteFieldById(field1.getId());
		int postCount = fieldDAO.getAllFields().size();
		
		assertEquals(initialCount - 1, postCount);
		assertNull(fieldDAO.getFieldById(field1.getId()));
		assertNotNull(fieldDAO.getFieldById(field2.getId()));
		assertNotNull(fieldDAO.getFieldById(field3.getId()));
		assertNotNull(fieldDAO.getFieldById(field4.getId()));
	}
	
	private Field testField(String name, String type) {
		Field field = new Field();
		field.setId(fieldDAO.generateUUID());
		field.setName(name);
		field.setType(type);
		return field;
	}
	
	private void assertFieldsAreEqual(Field field1, Field field2) {
		assertNotNull(field1);
		assertNotNull(field2);
		assertTrue(field1.getId().equals(field2.getId()));
		assertTrue(field1.getName().equals(field2.getName()));
		assertTrue(field1.getType().equals(field2.getType()));
	}

}