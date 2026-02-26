package com.revhire.revhire.service.implementation;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.revhire.revhire.dao.JobDAO;
import com.revhire.revhire.dao.implementation.JobDAOImpl;
import com.revhire.revhire.modals.Job;
import com.revhire.revhire.service.JobService;

public class JobServiceImpl implements JobService {
	private static final Logger logger = LogManager.getLogger(JobServiceImpl.class);

    private JobDAO jobDAO = new JobDAOImpl();
   
    @Override
    public int
    postJob(Job job) {

        if (job == null || job.getEmployerId() <= 0) 
            return -1;
        logger.info("Employer {} is creating job: {}", job.getEmployerId(), job.getTitle());
        return jobDAO.createJob(job);//jobId
    }
    
    @Override
    public List<Job> getAllOpenJobs() {
        return jobDAO.getAllOpenJobs();
    }

    @Override
    public List<Job> viewMyJobs(int employerId) {
        return jobDAO.getJobsByEmployerId(employerId);
    }
    @Override
    public List<Job> searchJobsByTitle(String title) {
        return jobDAO.searchByTitle(title);
    }

    @Override
    public List<Job> searchJobsByLocation(String location) {
        return jobDAO.searchByLocation(location);
    }

    @Override
    public List<Job> searchJobsByJobType(String jobType) {
        return jobDAO.searchByJobType(jobType);
    }

    @Override
    public Job viewJobDetails(int jobId) {
        return jobDAO.getJobById(jobId);
    }
    @Override
    public Job getJobById(int jobId) {
        return jobDAO.getJobById(jobId);
    }
    @Override
    public boolean updateJobStatus(int jobId, String status) {
        logger.info("Job status updated. Job ID: {} Status: {}", jobId, status);
        return jobDAO.updateJobStatus(jobId, status);
    }

    @Override
    public boolean closeJob(int jobId) {
        return jobDAO.updateJobStatus(jobId, "CLOSED");
    }

    @Override
    public boolean deleteJob(int jobId) {
        logger.warn("Job deleted. Job ID: {}", jobId);
        return jobDAO.deleteJob(jobId);
    }
    
	@Override
	public void createJob(Job job) {
		jobDAO.createJob(job);// TODO Auto-generated method stub
		
	}
}
