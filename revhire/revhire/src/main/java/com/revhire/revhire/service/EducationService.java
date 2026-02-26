package com.revhire.revhire.service;

import java.util.List;
import com.revhire.revhire.modals.Education;

public interface EducationService {

    boolean addEducation(Education education);

    List<Education> getEducationByResumeId(int resumeId);
}
