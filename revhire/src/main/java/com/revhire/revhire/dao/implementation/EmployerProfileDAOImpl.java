package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.EmployerProfileDAO;
import com.revhire.revhire.modals.EmployerProfile;

public class EmployerProfileDAOImpl implements EmployerProfileDAO {

    @Override
    public boolean createProfile(EmployerProfile profile) {

        String sql = "INSERT INTO employer_profile " +
                     "(user_id, company_name, industry, company_size, description, website, location) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getCompanyName());
            ps.setString(3, profile.getIndustry());
            ps.setString(4, profile.getCompanySize());
            ps.setString(5, profile.getDescription());
            ps.setString(6, profile.getWebsite());
            ps.setString(7, profile.getLocation());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public EmployerProfile getProfileByUserId(int userId) {

        String sql = "SELECT * FROM employer_profile WHERE user_id = ?";
        EmployerProfile profile = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                profile = new EmployerProfile();
                profile.setUserId(rs.getInt("user_id"));
                profile.setCompanyName(rs.getString("company_name"));
                profile.setIndustry(rs.getString("industry"));
                profile.setCompanySize(rs.getString("company_size"));
                profile.setDescription(rs.getString("description"));
                profile.setWebsite(rs.getString("website"));
                profile.setLocation(rs.getString("location"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profile;
    }

    @Override
    public boolean updateProfile(EmployerProfile profile) {

        String sql = "UPDATE employer_profile SET " +
                     "company_name=?, industry=?, company_size=?, description=?, website=?, location=? " +
                     "WHERE user_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, profile.getCompanyName());
            ps.setString(2, profile.getIndustry());
            ps.setString(3, profile.getCompanySize());
            ps.setString(4, profile.getDescription());
            ps.setString(5, profile.getWebsite());
            ps.setString(6, profile.getLocation());
            ps.setInt(7, profile.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
