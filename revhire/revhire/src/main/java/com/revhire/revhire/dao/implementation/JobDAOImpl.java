package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.JobDAO;
import com.revhire.revhire.dto.JobViewDTO;
import com.revhire.revhire.modals.Job;

public class JobDAOImpl implements JobDAO {

    // 1️⃣ Create Job
    @Override
    public int createJob(Job job) {

        String sql = """
            INSERT INTO jobs
            (employer_id, title, description, experience_required,
             education_required, location, salary_min, salary_max,
             job_type, deadline)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, job.getEmployerId());
            ps.setString(2, job.getTitle());
            ps.setString(3, job.getDescription());
            ps.setInt(4, job.getExperienceRequired());
            ps.setString(5, job.getEducationRequired());
            ps.setString(6, job.getLocation());
            ps.setDouble(7, job.getSalaryMin());
            ps.setDouble(8, job.getSalaryMax());
            ps.setString(9, job.getJobType());
            ps.setDate(10, job.getDeadline());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 2️⃣ Get Jobs by Employer
    @Override
    public List<Job> getJobsByEmployerId(int employerId) {

        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobs WHERE employer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                jobs.add(mapRowToJob(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }
    @Override
    public List<Job> getAllOpenJobs() {

        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobs WHERE status = 'OPEN'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                jobs.add(mapRowToJob(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }

    // 3️⃣ Get Job by ID
    @Override
    public Job getJobById(int jobId) {

        String sql = "SELECT * FROM jobs WHERE job_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToJob(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4️⃣ Update Job Status
    @Override
    public boolean updateJobStatus(int jobId, String status) {

        String sql = "UPDATE jobs SET status = ? WHERE job_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, jobId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5️⃣ Delete Job
    @Override
    public boolean deleteJob(int jobId) {

        String sql = "DELETE FROM jobs WHERE job_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6️⃣ Get All Jobs With Company Details (DTO)
    @Override
    public List<JobViewDTO> getAllJobsWithDetails() {

        List<JobViewDTO> jobs = new ArrayList<>();

        String sql = """
            SELECT 
                j.job_id,
                j.title,
                j.description,
                j.location,
                j.job_type,
                j.salary_min,
                j.salary_max,
                j.experience_required,
                j.education_required,
                j.status,
                j.deadline,
                e.company_name
            FROM jobs j
            JOIN employer_profiles e
                ON j.employer_id = e.employer_id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                JobViewDTO dto = new JobViewDTO();
                dto.setJobId(rs.getInt("job_id"));
                dto.setTitle(rs.getString("title"));
                dto.setDescription(rs.getString("description"));
                dto.setLocation(rs.getString("location"));
                dto.setJobType(rs.getString("job_type"));
                dto.setSalaryMin(rs.getDouble("salary_min"));
                dto.setSalaryMax(rs.getDouble("salary_max"));
                dto.setExperienceRequired(rs.getInt("experience_required"));
                dto.setEducationRequired(rs.getString("education_required"));
                dto.setStatus(rs.getString("status"));
                dto.setDeadline(rs.getDate("deadline"));
                dto.setCompanyName(rs.getString("company_name"));

                jobs.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }

    // 🔁 Helper mapper
    private Job mapRowToJob(ResultSet rs) throws Exception {

        Job job = new Job();
        job.setJobId(rs.getInt("job_id"));
        job.setEmployerId(rs.getInt("employer_id"));
        job.setTitle(rs.getString("title"));
        job.setDescription(rs.getString("description"));
        job.setExperienceRequired(rs.getInt("experience_required"));
        job.setEducationRequired(rs.getString("education_required"));
        job.setLocation(rs.getString("location"));
        job.setSalaryMin(rs.getDouble("salary_min"));
        job.setSalaryMax(rs.getDouble("salary_max"));
        job.setJobType(rs.getString("job_type"));
        job.setDeadline(rs.getDate("deadline"));
        job.setStatus(rs.getString("status"));
        job.setCreatedAt(rs.getTimestamp("created_at"));

        return job;
    }
}

