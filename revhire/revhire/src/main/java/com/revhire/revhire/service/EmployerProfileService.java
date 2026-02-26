package com.revhire.revhire.service;

import com.revhire.revhire.modals.EmployerProfile;

public interface EmployerProfileService {

    boolean createProfile(EmployerProfile profile);

    EmployerProfile getProfileByUserId(int userId);

    boolean updateProfile(EmployerProfile profile);
}
