package com.revhire.revhire.service.implementation;

import com.revhire.revhire.dao.ResumeSkillDAO;
import com.revhire.revhire.dao.SkillDAO;
import com.revhire.revhire.dao.implementation.ResumeSkillDAOImpl;
import com.revhire.revhire.dao.implementation.SkillDAOImpl;
import com.revhire.revhire.service.ResumeSkillService;

public class ResumeSkillServiceImpl implements ResumeSkillService {

    private SkillDAO skillDAO = new SkillDAOImpl();
    private ResumeSkillDAO resumeSkillDAO = new ResumeSkillDAOImpl();

    
    public boolean addSkillToResume(int resumeId, String skillName) {

        if (resumeId <= 0 || skillName == null || skillName.isBlank())
            return false;

        // 1️⃣ get or create skill
        int skillId = skillDAO.getSkillIdByName(skillName);

        if (skillId == -1) {
            skillId = skillDAO.addSkill(skillName);
        }

        if (skillId == -1) return false;

        // 2️⃣ link resume ↔ skill
        return resumeSkillDAO.addSkillToResume(resumeId, skillId);
    }
}
