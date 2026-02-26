package com.revhire.revhire.service;

import java.util.List;
import com.revhire.revhire.modals.Experience;

public interface ExperienceService {

    boolean addExperience(Experience experience);

    List<Experience> getExperienceByResumeId(int resumeId);
}
