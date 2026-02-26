package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.SkillDAO;

public class SkillDAOImpl implements SkillDAO {

    @Override
    public int getSkillIdByName(String skillName) {

        String sql = "SELECT skill_id FROM skills WHERE LOWER(skill_name) = LOWER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, skillName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("skill_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public int addSkill(String skillName) {

        String sql = "INSERT INTO skills (skill_name) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, skillName);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public boolean linkSkillToResume(int resumeId, int skillId) {

        String sql = "INSERT INTO resume_skills (resume_id, skill_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, resumeId);
            pstmt.setInt(2, skillId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }
}

