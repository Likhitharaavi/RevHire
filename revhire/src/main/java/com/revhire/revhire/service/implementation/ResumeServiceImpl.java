package com.revhire.revhire.service.implementation;

import com.revhire.revhire.dao.ResumeDAO;
import com.revhire.revhire.dao.implementation.ResumeDAOImpl;
import com.revhire.revhire.modals.Resume;
import com.revhire.revhire.service.ResumeService;

public class ResumeServiceImpl implements ResumeService {

    private ResumeDAO resumeDAO = new ResumeDAOImpl();

    @Override
    public boolean createResume(Resume resume) {

        if (resume == null) {
            return false;
        }

        if (resume.getUserId() <= 0) {
            return false;
        }

        if (resume.getObjective() == null || resume.getObjective().trim().isEmpty()) {
            return false;
        }

        // prevent duplicate resume creation
        Resume existing =
                resumeDAO.getResumeByUserId(resume.getUserId());

        if (existing != null) {
            return false;
        }

        return resumeDAO.createResume(resume);
    }
    @Override
    public boolean updateResume(Resume resume) {
        return resumeDAO.updateResume(resume);
    }
    @Override
    public Resume getResumeByUserId(int userId) {

        if (userId <= 0) {
            return null;
        }

        return resumeDAO.getResumeByUserId(userId);
    }
}
