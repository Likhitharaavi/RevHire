package com.revhire.revhire.dao;

import java.util.List;

import com.revhire.revhire.dto.JobViewDTO;
import com.revhire.revhire.modals.Job;
//import java.sql.PreparedStatement;

public interface JobDAO {

    int createJob(Job job);

    List<Job> getJobsByEmployerId(int employerId);
    
    List<Job>getAllOpenJobs();
    
    Job getJobById(int jobId);
    
    List<Job> searchByTitle(String title);
    
    List<Job> searchByLocation(String location);
    
    List<Job> searchByJobType(String jobType);
    
    boolean updateJobStatus(int jobId, String status);

    boolean deleteJob(int jobId);
    
    List<JobViewDTO> getAllJobsWithDetails();
}
