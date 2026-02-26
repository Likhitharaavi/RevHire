package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.ResumeSkillDAO;

public class ResumeSkillDAOImpl implements ResumeSkillDAO {

    @Override
    public boolean skillAlreadyMapped(int resumeId, int skillId) {

        String sql = "SELECT 1 FROM resume_skills WHERE resume_id = ? AND skill_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, resumeId);
            ps.setInt(2, skillId);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addSkillToResume(int resumeId, int skillId) {

        if (skillAlreadyMapped(resumeId, skillId)) {
            return true; // already linked, no error
        }

        String sql = "INSERT INTO resume_skills (resume_id, skill_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, resumeId);
            ps.setInt(2, skillId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
