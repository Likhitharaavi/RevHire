package com.revhire.revhire.dao;

import com.revhire.revhire.modals.EmployerProfile;

public interface EmployerProfileDAO {

    boolean createProfile(EmployerProfile profile);

    EmployerProfile getProfileByUserId(int userId);

    boolean updateProfile(EmployerProfile profile);
}
