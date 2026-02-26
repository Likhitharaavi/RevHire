package com.revhire.revhire.dao;

public interface ResumeSkillDAO {

    boolean addSkillToResume(int resumeId, int skillId);

    boolean skillAlreadyMapped(int resumeId, int skillId);
}
