package com.revhire.revhire.service.implementation;

import com.revhire.revhire.dao.EmployerProfileDAO;
import com.revhire.revhire.dao.implementation.EmployerProfileDAOImpl;
import com.revhire.revhire.modals.EmployerProfile;
import com.revhire.revhire.service.EmployerProfileService;

public class EmployerProfileServiceImpl implements EmployerProfileService {

    private EmployerProfileDAO employerProfileDAO =
            new EmployerProfileDAOImpl();

    @Override
    public boolean createProfile(EmployerProfile profile) {

        if (profile == null || profile.getUserId() <= 0) {
            return false;
        }

        return employerProfileDAO.createProfile(profile);
    }

    @Override
    public EmployerProfile getProfileByUserId(int userId) {
        return employerProfileDAO.getProfileByUserId(userId);
    }

    @Override
    public boolean updateProfile(EmployerProfile profile) {
        return employerProfileDAO.updateProfile(profile);
    }
}

