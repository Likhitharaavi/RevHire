package com.revhire.revhire.service;

import java.util.List;
import com.revhire.revhire.modals.Project;

public interface ProjectService {

    boolean addProject(Project project);

    List<Project> getProjectsByResumeId(int resumeId);
}
