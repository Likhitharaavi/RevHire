package com.revhire.revhire.dao;

import java.util.List;
import com.revhire.revhire.modals.Experience;

public interface ExperienceDAO {

    boolean addExperience(Experience experience);

    List<Experience> getExperienceByResumeId(int resumeId);
}
