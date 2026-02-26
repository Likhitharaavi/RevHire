package com.revhire.revhire.service.implementation;

import java.util.List;

import com.revhire.revhire.dao.ProjectDAO;
import com.revhire.revhire.dao.implementation.ProjectDAOImpl;
import com.revhire.revhire.modals.Project;
import com.revhire.revhire.service.ProjectService;

public class ProjectServiceImpl implements ProjectService {

    private ProjectDAO projectDAO = new ProjectDAOImpl();

    @Override
    public boolean addProject(Project project) {

        if (project == null) return false;

        if (project.getResumeId() <= 0) return false;

        if (project.getTitle() == null || project.getTitle().isBlank())
            return false;

        if (project.getDescription() == null || project.getDescription().isBlank())
            return false;

        return projectDAO.addProject(project);
    }

    @Override
    public List<Project> getProjectsByResumeId(int resumeId) {

        if (resumeId <= 0) return null;

        return projectDAO.getProjectsByResumeId(resumeId);
    }
}
