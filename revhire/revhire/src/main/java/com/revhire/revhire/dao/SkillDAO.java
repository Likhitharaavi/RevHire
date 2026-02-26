package com.revhire.revhire.dao;

public interface SkillDAO {

    int getSkillIdByName(String skillName);

    int addSkill(String skillName);

    boolean linkSkillToResume(int resumeId, int skillId);
}
