package com.revhire.revhire.service;

import java.util.List;
import com.revhire.revhire.modals.JobApplication;

public interface JobApplicationService {

    boolean applyJob(JobApplication application);
    
    List<JobApplication> viewMyApplications(int userId);

    List<JobApplication> viewApplicationsForJob(int jobId);
    
    boolean updateApplicationStatus(int applicationId, String status);

    boolean withdrawApplication(int applicationId, String reason);

    boolean updateApplicationStatus(
            int applicationId,
            String status,
            String employerComments
    );
}
