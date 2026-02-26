package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.EducationDAO;
import com.revhire.revhire.modals.Education;

public class EducationDAOImpl implements EducationDAO {

    @Override
    public boolean addEducation(Education education) {

        String sql = "INSERT INTO education "
                   + "(resume_id, degree, institution, start_year, end_year) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, education.getResumeId());
            pstmt.setString(2, education.getDegree());
            pstmt.setString(3, education.getInstitution());
            pstmt.setInt(4, education.getStartYear());
            pstmt.setInt(5, education.getEndYear());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Education> getEducationByResumeId(int resumeId) {

        List<Education> list = new ArrayList<>();
        String sql = "SELECT * FROM education WHERE resume_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, resumeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Education edu = new Education();
                    edu.setEducationId(rs.getInt("education_id"));
                    edu.setResumeId(rs.getInt("resume_id"));
                    edu.setDegree(rs.getString("degree"));
                    edu.setInstitution(rs.getString("institution"));
                    edu.setStartYear(rs.getInt("start_year"));
                    edu.setEndYear(rs.getInt("end_year"));
                    list.add(edu);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
