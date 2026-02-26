package com.revhire.revhire.service;

import java.util.List;
import com.revhire.revhire.modals.Job;

public interface JobService {

	void createJob(Job job);
	
    int postJob(Job job);
    
    Job getJobById(int jobId);
    
    List<Job> getAllOpenJobs();
    
    List<Job> searchJobsByTitle(String title);
    
    List<Job> searchJobsByLocation(String location);
    
    List<Job> searchJobsByJobType(String jobType);

    List<Job> viewMyJobs(int employerId);

    Job viewJobDetails(int jobId);
    
    boolean updateJobStatus(int jobId, String status);

    boolean closeJob(int jobId);

    boolean deleteJob(int jobId);
}
