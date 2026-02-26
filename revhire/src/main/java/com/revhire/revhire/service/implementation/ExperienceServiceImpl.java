package com.revhire.revhire.service.implementation;

import java.util.List;

import com.revhire.revhire.dao.ExperienceDAO;
import com.revhire.revhire.dao.implementation.ExperienceDAOImpl;
import com.revhire.revhire.modals.Experience;
import com.revhire.revhire.service.ExperienceService;

public class ExperienceServiceImpl implements ExperienceService {

    private ExperienceDAO experienceDAO = new ExperienceDAOImpl();

    @Override
    public boolean addExperience(Experience experience) {

        if (experience == null) return false;

        if (experience.getResumeId() <= 0) return false;

        if (experience.getCompany() == null || experience.getCompany().isBlank())
            return false;

        if (experience.getJobRole() == null || experience.getJobRole().isBlank())
            return false;

        if (experience.getStartDate() == null) return false;

        if (experience.getEndDate() != null &&
            experience.getStartDate().after(experience.getEndDate()))
            return false;

        return experienceDAO.addExperience(experience);
    }

    @Override
    public List<Experience> getExperienceByResumeId(int resumeId) {

        if (resumeId <= 0) return null;

        return experienceDAO.getExperienceByResumeId(resumeId);
    }
}
