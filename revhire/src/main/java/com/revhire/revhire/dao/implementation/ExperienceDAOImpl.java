package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.ExperienceDAO;
import com.revhire.revhire.modals.Experience;

public class ExperienceDAOImpl implements ExperienceDAO {

    @Override
    public boolean addExperience(Experience experience) {

        String sql = "INSERT INTO experience "
                   + "(resume_id, company, job_role, start_date, end_date, description) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, experience.getResumeId());
            pstmt.setString(2, experience.getCompany());
            pstmt.setString(3, experience.getJobRole());
            pstmt.setDate(4, experience.getStartDate());
            pstmt.setDate(5, experience.getEndDate());
            pstmt.setString(6, experience.getDescription());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Experience> getExperienceByResumeId(int resumeId) {

        List<Experience> list = new ArrayList<>();
        String sql = "SELECT * FROM experience WHERE resume_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, resumeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Experience exp = new Experience();
                    exp.setExperienceId(rs.getInt("experience_id"));
                    exp.setResumeId(rs.getInt("resume_id"));
                    exp.setCompany(rs.getString("company"));
                    exp.setJobRole(rs.getString("job_role"));
                    exp.setStartDate(rs.getDate("start_date"));
                    exp.setEndDate(rs.getDate("end_date"));
                    exp.setDescription(rs.getString("description"));
                    list.add(exp);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
