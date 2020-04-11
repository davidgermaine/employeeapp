package com.techelevator.DAOTests;

import static org.junit.Assert.assertEquals;
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
		
		Field field = testField();
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
	
	private Field testField() {
		Field field = new Field();
		field.setId(fieldDAO.generateUUID());
		field.setName("Test Name");
		field.setType("Test type of longer name");
		return field;
	}
	
	private void assertFieldsAreEqual(Field field1, Field field2) {
		assertTrue(field1.getId().equals(field2.getId()));
		assertTrue(field1.getName().equals(field2.getName()));
		assertTrue(field1.getType().equals(field2.getType()));
	}

}