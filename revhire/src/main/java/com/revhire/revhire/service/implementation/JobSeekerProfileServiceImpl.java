package com.revhire.revhire.service.implementation;

import com.revhire.revhire.dao.JobSeekerProfileDAO;
import com.revhire.revhire.dao.implementation.JobSeekerProfileDAOImpl;
import com.revhire.revhire.modals.JobSeekerProfile;
import com.revhire.revhire.service.JobSeekerProfileService;

public class JobSeekerProfileServiceImpl implements JobSeekerProfileService {

    private JobSeekerProfileDAO profileDAO = new JobSeekerProfileDAOImpl();

    @Override
    public boolean createProfile(JobSeekerProfile profile) {

        if (profile == null) {
            return false;
        }

        if (profile.getUserId() <= 0) {
            return false;
        }

        // ✅ prevent duplicate profile creation
        JobSeekerProfile existing =
                profileDAO.getProfileByUserId(profile.getUserId());

        if (existing != null) {
            return false; // profile already exists
        }

        return profileDAO.createProfile(profile);
    }

    @Override
    public JobSeekerProfile getProfileByUserId(int userId) {

        if (userId <= 0) {
            return null;
        }

        return profileDAO.getProfileByUserId(userId);
    }
}
