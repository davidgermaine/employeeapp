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
import com.techelevator.model.Skill;
import com.techelevator.model.SkillJDBCDAO;

public class SkillDAOTest {
	
	private static SingleConnectionDataSource dataSource;
	private SkillJDBCDAO skillDAO;
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
		skillDAO = new SkillJDBCDAO(dataSource);
		fieldDAO = new FieldJDBCDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void createSkill_adds_skill_to_database() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Field field = testField("Test Name", "Test Field");
		Skill skill = testSkill(field.getId(), 24, "");
		assertNotNull(skill);
		
		String sql = "SELECT COUNT(*) FROM skills";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		int initialCount = 0;
		while (result.next()) {
			initialCount = result.getInt("count");
		}
		
		skillDAO.createSkill(skill);
		int postCount = 0;
		result = jdbcTemplate.queryForRowSet(sql);
		while(result.next()) {
			postCount = result.getInt("count");
		}
		
		assertEquals(initialCount + 1, postCount);
	}
	
	@Test
	public void getSkillById_returns_proper_skill() {
		Field firstField = testField("Name 1", "Type 1");
		Skill firstSkill = testSkill(firstField.getId(), 24, "");
		skillDAO.createSkill(firstSkill);
		
		Field secondField = testField("Name 2", "Type 2");
		Skill secondSkill = testSkill(secondField.getId(), 36, "Summary with text in it");
		skillDAO.createSkill(secondSkill);
		
		Skill testFirstSkill = skillDAO.getSkillById(firstSkill.getId());
		Skill testSecondSkill = skillDAO.getSkillById(secondSkill.getId());
		assertSkillsAreEqual(firstSkill, testFirstSkill);
		assertSkillsAreEqual(secondSkill, testSecondSkill);
	}
	
	@Test
	public void deleteSkillById_deletes_skill_from_database() {
		Field firstField = testField("Name 1", "Type 1");
		Skill firstSkill = testSkill(firstField.getId(), 24, "");
		skillDAO.createSkill(firstSkill);
		
		Field secondField = testField("Name 2", "Type 2");
		Skill secondSkill = testSkill(secondField.getId(), 36, "Summary with text in it");
		skillDAO.createSkill(secondSkill);
		
		skillDAO.deleteSkillById(firstSkill.getId());
		assertNull(skillDAO.getSkillById(firstSkill.getId()));
		
		Skill testSecondSkill = skillDAO.getSkillById(secondSkill.getId());
		assertNotNull(testSecondSkill);
		assertSkillsAreEqual(secondSkill, testSecondSkill);
	}
	
	private Field testField(String name, String type) {
		Field field = new Field();
		field.setName(name);
		field.setType(type);
		fieldDAO.createField(field);
		return field;
	}
	
	private Skill testSkill(String fieldId, int experience, String summary) {
		Skill skill = new Skill();
    	skill.setField(fieldId);
    	skill.setExperience(experience);
    	skill.setSummary(summary);
    	return skill;
	}
	
	private void assertSkillsAreEqual(Skill skill1, Skill skill2) {
		assertNotNull(skill1);
		assertNotNull(skill2);
		assertTrue(skill1.getId().equals(skill2.getId()));
		assertTrue(skill1.getField().equals(skill2.getField()));
		assertTrue(skill1.getExperience() == skill2.getExperience());
		assertTrue(skill1.getSummary().equals(skill2.getSummary()));
	}

}
