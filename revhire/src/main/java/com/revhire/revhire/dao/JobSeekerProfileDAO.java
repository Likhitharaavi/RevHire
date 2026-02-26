package com.revhire.revhire.dao;

import com.revhire.revhire.modals.JobSeekerProfile;

public interface JobSeekerProfileDAO {

    // create profile
    boolean createProfile(JobSeekerProfile profile);

    // check if profile exists for a user
    JobSeekerProfile getProfileByUserId(int userId);
}
