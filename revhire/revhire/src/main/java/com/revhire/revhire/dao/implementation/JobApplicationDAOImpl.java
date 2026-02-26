package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.JobApplicationDAO;
import com.revhire.revhire.modals.JobApplication;

public class JobApplicationDAOImpl implements JobApplicationDAO {

    // 1️⃣ Apply Job
    @Override
    public boolean applyJob(JobApplication app) {

        String sql = """
            INSERT INTO job_applications
            (job_id, user_id, cover_letter)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, app.getJobId());
            ps.setInt(2, app.getUserId());
            ps.setString(3, app.getCoverLetter());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2️⃣ View applications by user
    @Override
    public List<JobApplication> getApplicationsByUserId(int userId) {

        List<JobApplication> list = new ArrayList<>();
        String sql = """
        	    SELECT 
        	        ja.application_id,
        	        ja.status,
        	        j.title AS job_title,
        	        j.location
        	    FROM job_applications ja
        	    JOIN jobs j ON ja.job_id = j.job_id
        	    WHERE ja.user_id = ?
        	""";


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3️⃣ View applications for a job (Employer)
    @Override
    public List<JobApplication> getApplicationsByJobId(int jobId) {

        List<JobApplication> list = new ArrayList<>();
        String sql = "SELECT * FROM job_applications WHERE job_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4️⃣ Withdraw application
    @Override
    public boolean withdrawApplication(int applicationId, String reason) {

        String sql = """
            UPDATE job_applications
            SET status = 'WITHDRAWN',
                withdrawal_reason = ?
            WHERE application_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reason);
            ps.setInt(2, applicationId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5️⃣ Employer updates status + comments
    @Override
    public boolean updateApplicationStatus(
            int applicationId,
            String status,
            String employerComments
    ) {

        String sql = """
            UPDATE job_applications
            SET status = ?,
                employer_comments = ?
            WHERE application_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, employerComments);
            ps.setInt(3, applicationId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔁 Mapper
    private JobApplication mapRow(ResultSet rs) throws Exception {

        JobApplication app = new JobApplication();
        app.setApplicationId(rs.getInt("application_id"));
        app.setStatus(rs.getString("status"));
        app.setJobTitle(rs.getString("job_title"));
        app.setLocation(rs.getString("location"));

        return app;
    }
}
