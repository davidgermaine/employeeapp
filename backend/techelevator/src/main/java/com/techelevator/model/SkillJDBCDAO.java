package com.techelevator.model;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class SkillJDBCDAO implements SkillDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
    public SkillJDBCDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	private Skill mapRowToSkill(SqlRowSet result) {
    	Skill skill = new Skill();
    	skill.setId(result.getString("id"));
    	skill.setField(result.getString("field"));
    	skill.setExperience(result.getInt("experience"));
    	skill.setSummary(result.getString("summary"));
    	return skill;
    }
    
    public String generateUUID() {
    	UUID uuid = UUID.randomUUID();
    	return "" + uuid;
    }

	@Override
	public void createSkill(Skill skill) {
		String sql = "INSERT INTO skills (id, field, experience, summary) VALUES (?, ?, ?, ?)";
		skill.setId(generateUUID());
		jdbcTemplate.update(sql, skill.getId(), skill.getField(), skill.getExperience(), skill.getSummary());
	}
	
	@Override
	public Skill getSkillById(String skillId) {
		String sql = "SELECT * FROM skills WHERE id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, skillId);
		Skill skill = null;
		while (result.next()) {
			skill = mapRowToSkill(result);
		}
		return skill;
	}
	
	@Override
	public void updateSkillById(String skillId, Skill skill) {
		String sql = "UPDATE skills SET field = ?, experience = ?, summary = ? WHERE id = ?";
		jdbcTemplate.update(sql, skill.getField(), skill.getExperience(), skill.getSummary(), skillId);
	}

	@Override
	public void deleteSkillById(String skillId) {
		String sql = "DELETE FROM skills WHERE id = ?";
		jdbcTemplate.update(sql, skillId);
	}

}