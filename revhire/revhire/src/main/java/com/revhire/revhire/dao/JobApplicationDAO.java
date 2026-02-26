package com.revhire.revhire.dao;

import java.util.List;
import com.revhire.revhire.modals.JobApplication;

public interface JobApplicationDAO {

    boolean applyJob(JobApplication application);

    List<JobApplication> getApplicationsByUserId(int userId);

    List<JobApplication> getApplicationsByJobId(int jobId);

    boolean withdrawApplication(int applicationId, String reason);

    boolean updateApplicationStatus(
            int applicationId,
            String status,
            String employerComments
    );
}
