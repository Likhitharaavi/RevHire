package com.revhire.revhire.service.implementation;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.revhire.revhire.dao.NotificationDAO;
import com.revhire.revhire.dao.implementation.NotificationDAOImpl;
import com.revhire.revhire.modals.Notification;
import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.JobApplicationDAO;
import com.revhire.revhire.dao.implementation.JobApplicationDAOImpl;
import com.revhire.revhire.modals.JobApplication;
import com.revhire.revhire.service.JobApplicationService;

public class JobApplicationServiceImpl implements JobApplicationService {
	private static final Logger logger = LogManager.getLogger(JobApplicationServiceImpl.class);

    private JobApplicationDAO applicationDAO = new JobApplicationDAOImpl();
    
    private NotificationDAO notificationDAO = new NotificationDAOImpl();

    @Override
    public boolean applyJob(JobApplication application) {

        if (application == null ||
            application.getJobId() <= 0 ||
            application.getUserId() <= 0)
            return false;

        boolean applied = applicationDAO.applyJob(application);

        if (applied) {

            int employerId = getEmployerIdByJobId(application.getJobId());

            Notification notification = new Notification();
            notification.setUserId(employerId);
            notification.setMessage("New application received for your job.");
            notification.setRead(false);

            notificationDAO.addNotification(notification);
        }
        logger.info("User {} applied for Job {}", application.getUserId(), application.getJobId());

        return applied;
    }


    @Override
    public List<JobApplication> viewMyApplications(int userId) {
        return applicationDAO.getApplicationsByUserId(userId);
    }
    public List<JobApplication> viewApplicationsByJobId(int jobId) {

        List<JobApplication> list = new ArrayList<>();

        String sql = "SELECT * FROM job_applications WHERE job_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JobApplication app = new JobApplication();
                app.setApplicationId(rs.getInt("application_id"));
                app.setJobId(rs.getInt("job_id"));
                app.setCandidateId(rs.getInt("candidate_id"));
                app.setStatus(rs.getString("status"));
                app.setAppliedAt(rs.getTimestamp("applied_at"));
                list.add(app);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    public boolean withdrawApplication(int applicationId, String reason) {
    	logger.info("Application {} withdrawn by user {}", applicationId);

        boolean updated = applicationDAO.withdrawApplication(
                applicationId,
                "Withdrawn by candidate"
        );

        if (updated) {

            int employerId = getEmployerIdByApplicationId(applicationId);

            Notification notification = new Notification();
            notification.setUserId(employerId);
            String jobTitle = getJobTitleByApplicationId(applicationId);

            notification.setMessage(
                "A candidate has withdrawn application for '" + jobTitle + "'"
            );
            notification.setRead(false);

            notificationDAO.addNotification(notification);
        }
        
        return updated;
    }

    @Override
    public boolean updateApplicationStatus(
            int applicationId,
            String status,
            String employerComments
    ) {
    	logger.info("Application {} status changed to {}", applicationId, status);

        boolean updated = applicationDAO.updateApplicationStatus(
                applicationId,
                status,
                employerComments
        );

        if (updated) {

            int candidateId = getCandidateIdByApplicationId(applicationId);

            Notification notification = new Notification();
            notification.setUserId(candidateId);
            String jobTitle = getJobTitleByApplicationId(applicationId);

            notification.setMessage(
                "Your application for '" + jobTitle + "' has been " + status
            );

            notification.setRead(false);

            notificationDAO.addNotification(notification);
        }

        return updated;
    }

    @Override
    public boolean updateApplicationStatus(int applicationId, String status) {
        return updateApplicationStatus(
                applicationId,
                status,
                "Updated by employer"
        );
    }

	@Override
	public List<JobApplication> viewApplicationsForJob(int jobId) {
		// TODO Auto-generated method stub
		return applicationDAO.getApplicationsByJobId(jobId);
	}
	private int getEmployerIdByApplicationId(int applicationId) {

	    String sql = """
	        SELECT j.employer_id
	        FROM job_applications ja
	        JOIN jobs j ON ja.job_id = j.job_id
	        WHERE ja.application_id = ?
	    """;

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, applicationId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("employer_id");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return 0;
	}
	private int getCandidateIdByApplicationId(int applicationId) {

	    String sql = "SELECT user_id FROM job_applications WHERE application_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, applicationId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("user_id");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return 0;
	}
	private int getEmployerIdByJobId(int jobId) {

	    String sql = "SELECT employer_id FROM jobs WHERE job_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, jobId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("employer_id");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return 0;
	}

	private String getJobTitleByApplicationId(int applicationId) {

	    String sql = """
	        SELECT j.title
	        FROM job_applications ja
	        JOIN jobs j ON ja.job_id = j.job_id
	        WHERE ja.application_id = ?
	    """;

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, applicationId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getString("title");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return "your job";
	}

	}



