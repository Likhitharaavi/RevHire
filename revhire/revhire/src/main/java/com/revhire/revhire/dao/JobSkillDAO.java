package com.revhire.revhire.dao;

//public interface JobSkillDAO {

//    boolean addSkillToJob(int jobId, int skillId);

//    boolean skillAlreadyMapped(int jobId, int skillId);
//}
public interface JobSkillDAO {

    int getSkillIdByName(String skillName);

    int addSkill(String skillName);

    boolean addSkillToJob(int jobId, int skillId);
}

