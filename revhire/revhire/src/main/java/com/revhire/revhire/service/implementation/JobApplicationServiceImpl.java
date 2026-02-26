package com.revhire.revhire.service.implementation;

import java.util.List;

import com.revhire.revhire.dao.JobApplicationDAO;
import com.revhire.revhire.dao.implementation.JobApplicationDAOImpl;
import com.revhire.revhire.modals.JobApplication;
import com.revhire.revhire.service.JobApplicationService;

public class JobApplicationServiceImpl implements JobApplicationService {

    private JobApplicationDAO applicationDAO =
            new JobApplicationDAOImpl();

    @Override
    public boolean applyJob(JobApplication application) {

        if (application == null ||
            application.getJobId() <= 0 ||
            application.getUserId() <= 0)
            return false;

        return applicationDAO.applyJob(application);
    }

    @Override
    public List<JobApplication> viewMyApplications(int userId) {
        return applicationDAO.getApplicationsByUserId(userId);
    }

    @Override
    public List<JobApplication> viewApplicationsForJob(int jobId) {
        return applicationDAO.getApplicationsByJobId(jobId);
    }

    @Override
    public boolean withdrawApplication(int applicationId, String reason) {
        return applicationDAO.withdrawApplication(applicationId, reason);
    }

    @Override
    public boolean updateApplicationStatus(
            int applicationId,
            String status,
            String employerComments
    ) {
        return applicationDAO.updateApplicationStatus(
                applicationId,
                status,
                employerComments
        );
    }
}
