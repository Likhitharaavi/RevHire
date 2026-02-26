package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.JobSeekerProfileDAO;
import com.revhire.revhire.modals.JobSeekerProfile;

public class JobSeekerProfileDAOImpl implements JobSeekerProfileDAO {

    // 1️⃣ Create Job Seeker Profile
    @Override
    public boolean createProfile(JobSeekerProfile profile) {

        String sql = "INSERT INTO job_seeker_profile "
                   + "(user_id, full_name, phone, location, total_experience) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, profile.getUserId());
            pstmt.setString(2, profile.getFullName());
            pstmt.setString(3, profile.getPhone());
            pstmt.setString(4, profile.getLocation());
            pstmt.setInt(5, profile.getTotalExperience());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2️⃣ Get profile by userId (also used to check if profile exists)
    @Override
    public JobSeekerProfile getProfileByUserId(int userId) {

        String sql = "SELECT * FROM job_seeker_profile WHERE user_id = ?";
        JobSeekerProfile profile = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    profile = new JobSeekerProfile();
                    profile.setUserId(rs.getInt("user_id"));
                    profile.setFullName(rs.getString("full_name"));
                    profile.setPhone(rs.getString("phone"));
                    profile.setLocation(rs.getString("location"));
                    profile.setTotalExperience(rs.getInt("total_experience"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }
}
