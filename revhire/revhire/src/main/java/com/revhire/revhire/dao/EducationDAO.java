package com.revhire.revhire.dao;

import java.util.List;
import com.revhire.revhire.modals.Education;

public interface EducationDAO {

    // add education details
    boolean addEducation(Education education);

    // fetch all education records for a resume
    List<Education> getEducationByResumeId(int resumeId);
}
