package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.CertificationDAO;
import com.revhire.revhire.modals.Certification;

public class CertificationDAOImpl implements CertificationDAO {

    // 1️⃣ Add certification
    @Override
    public boolean addCertification(Certification certification) {

        String sql =
            "INSERT INTO certifications (user_id, cert_name, issuing_organization, issue_date) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, certification.getUserId());
            pstmt.setString(2, certification.getCertName());
            pstmt.setString(3, certification.getIssuingOrganization());
            pstmt.setDate(4, certification.getIssueDate());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2️⃣ Get all certifications by userId
    @Override
    public List<Certification> getCertificationsByUserId(int userId) {

        List<Certification> list = new ArrayList<>();

        String sql =
            "SELECT * FROM certifications WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    Certification cert = new Certification();
                    cert.setCertId(rs.getInt("cert_id"));
                    cert.setUserId(rs.getInt("user_id"));
                    cert.setCertName(rs.getString("cert_name"));
                    cert.setIssuingOrganization(
                            rs.getString("issuing_organization"));
                    cert.setIssueDate(rs.getDate("issue_date"));

                    list.add(cert);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 3️⃣ Get certification by certId
    @Override
    public Certification getCertificationById(int certId) {

        String sql =
            "SELECT * FROM certifications WHERE cert_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, certId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    Certification cert = new Certification();
                    cert.setCertId(rs.getInt("cert_id"));
                    cert.setUserId(rs.getInt("user_id"));
                    cert.setCertName(rs.getString("cert_name"));
                    cert.setIssuingOrganization(rs.getString("issuing_organization"));
                    cert.setIssueDate(rs.getDate("issue_date"));

                    return cert;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 4️⃣ Update certification
    @Override
    public boolean updateCertification(Certification certification) {

        String sql =
            "UPDATE certifications " +
            "SET cert_name = ?, issuing_organization = ?, issue_date = ? " +
            "WHERE cert_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, certification.getCertName());
            pstmt.setString(2, certification.getIssuingOrganization());
            pstmt.setDate(3, certification.getIssueDate());
            pstmt.setInt(4, certification.getCertId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5️⃣ Delete certification
    @Override
    public boolean deleteCertification(int certId) {

        String sql =
            "DELETE FROM certifications WHERE cert_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, certId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
