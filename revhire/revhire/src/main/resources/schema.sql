-- ==========================================================
-- 1. MASTER AUTHENTICATION
-- ==========================================================
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('JOB_SEEKER', 'EMPLOYER') NOT NULL,
    security_question VARCHAR(255),
    security_answer VARCHAR(255),
    profile_completion_percent INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_profile_percent CHECK (profile_completion_percent BETWEEN 0 AND 100)
);

-- ==========================================================
-- 2. PROFILES
-- ==========================================================
CREATE TABLE IF NOT EXISTS job_seeker_profile (
    user_id INT PRIMARY KEY,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    location VARCHAR(100),
    total_experience DECIMAL(4,2),
    CONSTRAINT fk_js_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS employer_profile (
    user_id INT PRIMARY KEY,
    company_name VARCHAR(150),
    industry VARCHAR(100),
    company_size VARCHAR(50),
    description TEXT,
    website VARCHAR(150),
    location VARCHAR(100),
    CONSTRAINT fk_emp_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ==========================================================
-- 3. RESUME & SUPPLEMENTARY SECTIONS
-- ==========================================================
CREATE TABLE IF NOT EXISTS resume (
    resume_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    objective TEXT,
    CONSTRAINT fk_resume_user FOREIGN KEY (user_id) REFERENCES job_seeker_profile(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS education (
    education_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT,
    degree VARCHAR(100),
    institution VARCHAR(150),
    start_year INT,
    end_year INT,
    CONSTRAINT fk_edu_resume FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS experience (
    experience_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT,
    company VARCHAR(150),
    job_role VARCHAR(100),
    start_date DATE,
    end_date DATE,
    description TEXT,
    CONSTRAINT fk_exp_resume FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS projects (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT,
    title VARCHAR(150),
    description TEXT,
    tech_stack VARCHAR(255),
    CONSTRAINT fk_proj_resume FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS certifications (
    cert_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    cert_name VARCHAR(150),
    issuing_organization VARCHAR(150),
    issue_date DATE,
    CONSTRAINT fk_cert_user FOREIGN KEY (user_id) REFERENCES job_seeker_profile(user_id) ON DELETE CASCADE
);

-- ==========================================================
-- 4. SKILLS (Many-to-Many)
-- ==========================================================
CREATE TABLE IF NOT EXISTS skills (
    skill_id INT AUTO_INCREMENT PRIMARY KEY,
    skill_name VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS resume_skills (
    resume_id INT,
    skill_id INT,
    PRIMARY KEY (resume_id, skill_id),
    CONSTRAINT fk_rs_resume FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE,
    CONSTRAINT fk_rs_skill FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE
);

-- ==========================================================
-- 5. JOBS & APPLICATIONS
-- ==========================================================
CREATE TABLE IF NOT EXISTS jobs (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    employer_id INT,
    title VARCHAR(150),
    description TEXT,
    experience_required INT,
    education_required VARCHAR(100),
    location VARCHAR(100),
    salary_min DECIMAL(15,2),
    salary_max DECIMAL(15,2),
    job_type ENUM('FULL_TIME', 'PART_TIME', 'INTERNSHIP', 'CONTRACT'),
    deadline DATE,
    status ENUM('OPEN', 'CLOSED') DEFAULT 'OPEN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_job_employer FOREIGN KEY (employer_id) REFERENCES employer_profile(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS job_skills (
    job_id INT,
    skill_id INT,
    PRIMARY KEY (job_id, skill_id),
    CONSTRAINT fk_jskill_job FOREIGN KEY (job_id) REFERENCES jobs(job_id) ON DELETE CASCADE,
    CONSTRAINT fk_jskill_skill FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS job_applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    job_id INT,
    user_id INT,
    status ENUM('APPLIED', 'SHORTLISTED', 'REJECTED', 'WITHDRAWN') DEFAULT 'APPLIED',
    cover_letter TEXT
);