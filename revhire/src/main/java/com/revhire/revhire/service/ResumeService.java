package com.revhire.revhire.service;

import com.revhire.revhire.modals.Resume;

public interface ResumeService {

    boolean createResume(Resume resume);
    
    boolean updateResume(Resume resume);

    Resume getResumeByUserId(int userId);
}
