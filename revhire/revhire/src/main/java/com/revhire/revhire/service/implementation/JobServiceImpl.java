package com.revhire.revhire.service.implementation;

import java.util.List;

import com.revhire.revhire.dao.JobDAO;
import com.revhire.revhire.dao.implementation.JobDAOImpl;
import com.revhire.revhire.modals.Job;
import com.revhire.revhire.service.JobService;

public class JobServiceImpl implements JobService {

    private JobDAO jobDAO = new JobDAOImpl();

    @Override
    public int
    postJob(Job job) {

        if (job == null || job.getEmployerId() <= 0) 
            return -1;
        
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
    public Job viewJobDetails(int jobId) {
        return jobDAO.getJobById(jobId);
    }

    @Override
    public boolean closeJob(int jobId) {
        return jobDAO.updateJobStatus(jobId, "CLOSED");
    }

    @Override
    public boolean deleteJob(int jobId) {
        return jobDAO.deleteJob(jobId);
    }

	@Override
	public void createJob(Job job) {
		jobDAO.createJob(job);// TODO Auto-generated method stub
		
	}
}
