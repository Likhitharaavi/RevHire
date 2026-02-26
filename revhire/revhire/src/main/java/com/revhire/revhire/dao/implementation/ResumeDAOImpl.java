package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.ResumeDAO;
import com.revhire.revhire.modals.Resume;

public class ResumeDAOImpl implements ResumeDAO {

    public boolean createResume(Resume resume) {

        String sql = "INSERT INTO resume (user_id, objective) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, resume.getUserId());
            pstmt.setString(2, resume.getObjective());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Resume getResumeByUserId(int userId) {

        String sql = "SELECT * FROM resume WHERE user_id = ?";
        Resume resume = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    resume = new Resume();
                    resume.setResumeId(rs.getInt("resume_id"));
                    resume.setUserId(rs.getInt("user_id"));
                    resume.setObjective(rs.getString("objective"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resume;
    }
}
