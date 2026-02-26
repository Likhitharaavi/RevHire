package com.revhire.revhire.service.implementation;

import com.revhire.revhire.dao.SkillDAO;
import com.revhire.revhire.dao.implementation.JobSkillDAOImpl;
import com.revhire.revhire.dao.implementation.SkillDAOImpl;
import com.revhire.revhire.service.JobSkillService;
import com.revhire.revhire.dao.JobSkillDAO;

public class JobSkillServiceImpl implements JobSkillService {

    private SkillDAO skillDAO = new SkillDAOImpl();
    private JobSkillDAO jobSkillDAO = new JobSkillDAOImpl();

    @Override
    public boolean addSkillToJob(int jobId, String skillName) {

        if (jobId <= 0 || skillName == null || skillName.isBlank())
            return false;

        int skillId = skillDAO.getSkillIdByName(skillName);

        if (skillId == -1) {
            skillId = skillDAO.addSkill(skillName);
        }

        if (skillId == -1) return false;

        return jobSkillDAO.addSkillToJob(jobId, skillId);
    }
}
