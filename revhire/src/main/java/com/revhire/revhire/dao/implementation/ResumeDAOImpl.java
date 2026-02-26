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
    @Override
    public boolean updateResume(Resume resume) {

        String sql = "UPDATE resume SET objective = ? WHERE resume_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, resume.getObjective());
            ps.setInt(2, resume.getResumeId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
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
