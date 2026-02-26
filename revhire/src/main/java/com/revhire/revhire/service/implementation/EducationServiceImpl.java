package com.revhire.revhire.service.implementation;

import java.util.List;

import com.revhire.revhire.dao.EducationDAO;
import com.revhire.revhire.dao.implementation.EducationDAOImpl;
import com.revhire.revhire.modals.Education;
import com.revhire.revhire.service.EducationService;

public class EducationServiceImpl implements EducationService {

    private EducationDAO educationDAO = new EducationDAOImpl();

    @Override
    public boolean addEducation(Education education) {

        if (education == null) {
            return false;
        }

        if (education.getResumeId() <= 0) {
            return false;
        }

        if (education.getDegree() == null || education.getDegree().trim().isEmpty()) {
            return false;
        }

        if (education.getInstitution() == null || education.getInstitution().trim().isEmpty()) {
            return false;
        }

        if (education.getStartYear() > education.getEndYear()) {
            return false;
        }

        return educationDAO.addEducation(education);
    }

    @Override
    public List<Education> getEducationByResumeId(int resumeId) {

        if (resumeId <= 0) {
            return null;
        }

        return educationDAO.getEducationByResumeId(resumeId);
    }
}
