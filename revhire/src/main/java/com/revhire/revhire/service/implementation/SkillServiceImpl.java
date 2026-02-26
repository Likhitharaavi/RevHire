package com.revhire.revhire.service.implementation;

import com.revhire.revhire.dao.SkillDAO;
import com.revhire.revhire.dao.implementation.SkillDAOImpl;
import com.revhire.revhire.service.SkillService;

public class SkillServiceImpl implements SkillService {

    private SkillDAO skillDAO = new SkillDAOImpl();

    public boolean addSkillToResume(int resumeId, String skillName) {

        if (resumeId <= 0 || skillName == null || skillName.isBlank())
            return false;

        // 1️⃣ Check if skill already exists
        int skillId = skillDAO.getSkillIdByName(skillName);

        // 2️⃣ If not exists → insert
        if (skillId == -1) {
            skillId = skillDAO.addSkill(skillName);
        }

        if (skillId == -1) return false;

        // 3️⃣ Link resume ↔ skill
        return skillDAO.linkSkillToResume(resumeId, skillId);
    }
}
