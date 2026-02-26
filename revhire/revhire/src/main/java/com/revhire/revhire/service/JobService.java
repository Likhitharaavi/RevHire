package com.revhire.revhire.service;

import java.util.List;
import com.revhire.revhire.modals.Job;

public interface JobService {

	void createJob(Job job);
	
    int postJob(Job job);
    
    List<Job> getAllOpenJobs();

    List<Job> viewMyJobs(int employerId);

    Job viewJobDetails(int jobId);

    boolean closeJob(int jobId);

    boolean deleteJob(int jobId);
}
