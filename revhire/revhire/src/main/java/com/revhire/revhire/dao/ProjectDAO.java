package com.revhire.revhire.dao;

import java.util.List;
import com.revhire.revhire.modals.Project;

public interface ProjectDAO {

    boolean addProject(Project project);

    List<Project> getProjectsByResumeId(int resumeId);
}
