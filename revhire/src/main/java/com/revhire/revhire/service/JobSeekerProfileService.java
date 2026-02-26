package com.revhire.revhire.service;

import com.revhire.revhire.modals.JobSeekerProfile;

public interface JobSeekerProfileService {

    // create profile
    boolean createProfile(JobSeekerProfile profile);

    // check / fetch profile
    JobSeekerProfile getProfileByUserId(int userId);
}
