package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.JobSkillDAO;

public class JobSkillDAOImpl implements JobSkillDAO {
	    
	    public int getSkillIdByName(String skillName) {

	        String sql = "SELECT skill_id FROM skills WHERE LOWER(skill_name) = LOWER(?)";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, skillName);

	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return rs.getInt("skill_id");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return -1;
	    }

	    public int addSkill(String skillName) {

	        String sql = "INSERT INTO skills (skill_name) VALUES (?)";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps =
	                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	            ps.setString(1, skillName);
	            ps.executeUpdate();

	            ResultSet rs = ps.getGeneratedKeys();
	            if (rs.next()) {
	                return rs.getInt(1);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return -1;
	    }

	    // 🔴 THIS IS THE METHOD YOU ARE MISSING
	    public boolean addSkillToJob(int jobId, int skillId) {

	        String sql = "INSERT INTO job_skills (job_id, skill_id) VALUES (?, ?)";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, jobId);
	            ps.setInt(2, skillId);

	            return ps.executeUpdate() > 0;

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return false;
	    }
	}
