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
public class EmpSkillJDBCDAO implements EmpSkillDAO {
	
	private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmpSkillJDBCDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
//    private EmpSkill mapRowToEmpSkill(SqlRowSet result) {
//    	EmpSkill empskill = new EmpSkill();
//    	empskill.setId(result.getString("id"));
//    	empskill.setEmployee("employee");
//    	empskill.setSkill("skill");
//    	return empskill;
//    }
    
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
	public List<Skill> getAllSkillsByEmployeeId(String employeeId) {
		List<Skill> skillList = new ArrayList<>();
		String sql = "SELECT skills.* FROM skills "
				+ "JOIN employee_skills ON skills.id = employee_skills.skill "
				+ "WHERE employee_skills.employee = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, employeeId);
		while (result.next()) {
			Skill skill = mapRowToSkill(result);
			skillList.add(skill);
		}
		return skillList;
	}

	@Override
	public void addSkillToEmployee(String employeeId, String skillId) {
		String sql = "INSERT INTO employee_skills (id, employee, skill) VALUES (?, ?, ?)";
		EmpSkill empskill = new EmpSkill();
		empskill.setId(generateUUID());
		empskill.setEmployee(employeeId);
		empskill.setSkill(skillId);
		jdbcTemplate.update(sql, empskill.getId(), empskill.getEmployee(), empskill.getSkill());
	}

	@Override
	public Skill getSkillFromEmployeeById(String employeeId, String skillId) {
		String sql = "SELECT skills.* FROM skills "
				+ "JOIN employee_skills ON skills.id = employee_skills.skill "
				+ "WHERE employee_skills.employee = ? AND employee_skills.skill = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, employeeId, skillId);
		Skill skill = null;
		while (result.next()) {
			skill = mapRowToSkill(result);
		}
		return skill;
	}

	@Override
	public void updateSkillFromEmployeeById(String employeeId, String skillId, Skill updatedSkill) {
		String sql = "UPDATE skills SET field = ?, experience = ?, summary = ? "
				+ "FROM employee_skills "
				+ "WHERE employee_skills.employee = ? AND employee_skills.skill = ?";
		updatedSkill.setId(skillId);
		jdbcTemplate.update(sql, updatedSkill.getField(), updatedSkill.getExperience(), updatedSkill.getSummary(), 
				employeeId, skillId);
	}

	@Override
	public void deleteSkillFromEmployeeById(String employeeId, String skillId) {
		String sql = "DELETE FROM employee_skills WHERE employee = ? AND skill = ?";
		jdbcTemplate.update(sql, employeeId, skillId);
	}

}
