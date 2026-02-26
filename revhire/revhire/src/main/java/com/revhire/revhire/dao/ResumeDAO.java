package com.revhire.revhire.dao;

import com.revhire.revhire.modals.Resume;

public interface ResumeDAO {

    // create resume
    boolean createResume(Resume resume);

    // fetch resume by userId
    Resume getResumeByUserId(int userId);
}
