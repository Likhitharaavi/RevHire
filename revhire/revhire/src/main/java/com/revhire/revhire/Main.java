package com.revhire.revhire;

import java.util.Scanner;
import java.util.List;
import com.revhire.revhire.modals.Notification;
import com.revhire.revhire.service.NotificationService;
import com.revhire.revhire.service.implementation.NotificationServiceImpl;
import java.sql.Timestamp;
import com.revhire.revhire.service.JobSkillService;
import com.revhire.revhire.service.implementation.JobSkillServiceImpl;
import com.revhire.revhire.modals.EmployerProfile;
import com.revhire.revhire.service.EmployerProfileService;
import com.revhire.revhire.service.implementation.EmployerProfileServiceImpl;
import com.revhire.revhire.modals.Job;
import com.revhire.revhire.modals.JobApplication;
import com.revhire.revhire.service.JobService;
import com.revhire.revhire.service.JobApplicationService;
import com.revhire.revhire.service.implementation.JobServiceImpl;
import com.revhire.revhire.service.implementation.JobApplicationServiceImpl;
import com.revhire.revhire.service.SkillService;
import com.revhire.revhire.service.implementation.SkillServiceImpl;
import com.revhire.revhire.modals.Project;
import com.revhire.revhire.service.ProjectService;
import com.revhire.revhire.service.implementation.ProjectServiceImpl;
import com.revhire.revhire.modals.Experience;
import com.revhire.revhire.service.ExperienceService;
import com.revhire.revhire.service.implementation.ExperienceServiceImpl;
import java.sql.Date;
import com.revhire.revhire.modals.Education;
import com.revhire.revhire.service.EducationService;
import com.revhire.revhire.service.implementation.EducationServiceImpl;
import com.revhire.revhire.modals.Resume;
import com.revhire.revhire.service.ResumeService;
import com.revhire.revhire.service.implementation.ResumeServiceImpl;
import com.revhire.revhire.modals.JobSeekerProfile;
import com.revhire.revhire.modals.User;
import com.revhire.revhire.service.JobSeekerProfileService;
import com.revhire.revhire.service.UserService;
import com.revhire.revhire.service.implementation.JobSeekerProfileServiceImpl;
import com.revhire.revhire.service.implementation.UserServiceImpl;

public class Main {

  public static void main(String[] args) {

      Scanner sc = new Scanner(System.in);

      UserService userService = new UserServiceImpl();
      JobSeekerProfileService profileService = new JobSeekerProfileServiceImpl();
      ResumeService resumeService = new ResumeServiceImpl();
      EducationService educationService = new EducationServiceImpl();
      ExperienceService experienceService = new ExperienceServiceImpl();
      ProjectService projectService = new ProjectServiceImpl();
      SkillService skillService = new SkillServiceImpl();
      EmployerProfileService employerProfileService = new EmployerProfileServiceImpl();
      JobSkillService jobSkillService = new JobSkillServiceImpl();
      NotificationService notificationService = new NotificationServiceImpl();
   
      
    System.out.println("===== USER REGISTRATION =====");

    User user = new User();

    System.out.print("Enter Email: ");
    user.setEmail(sc.nextLine());

    System.out.print("Enter Password: ");
    user.setPassword(sc.nextLine());

    System.out.print("Enter Role (JOB_SEEKER / EMPLOYER): ");
    user.setRole(sc.nextLine().trim().toUpperCase());

    System.out.print("Enter Security Question: ");
    user.setSecurityQuestion(sc.nextLine());

    System.out.print("Enter Security Answer: ");
    user.setSecurityAnswer(sc.nextLine());

    String result = userService.registerUser(user);

    User loggedInUser = null;

    // ---------- REGISTRATION RESULT ----------
    if ("SUCCESS".equals(result)) {

        System.out.println("✅ Registration successful!");
        System.out.println("👉 Please login to continue.\n");

    } else if ("EMAIL_EXISTS".equals(result)) {

        System.out.println("⚠️ Email already registered.");
        System.out.println("👉 Please login.\n");

    } else {
        System.out.println("❌ Registration failed: " + result);
        sc.close();
        return;
    }

    // ---------- LOGIN ----------
    System.out.print("Enter Email: ");
    String loginEmail = sc.nextLine();

    System.out.print("Enter Password: ");
    String loginPassword = sc.nextLine();

    loggedInUser = userService.login(loginEmail, loginPassword);

    if (loggedInUser == null) {
        System.out.println("❌ Invalid login credentials.");
        sc.close();
        return;
    }

    System.out.println("✅ Login successful!");
    System.out.println("Welcome, " + loggedInUser.getEmail());

    Resume resume = null;
      
    // =====================================================
   // ROLE CHECK
   // =====================================================
   if ("JOB_SEEKER".equals(loggedInUser.getRole())) {

       // =================================================
       // JOB SEEKER PROFILE
       // =================================================
       JobSeekerProfile existingProfile =
               profileService.getProfileByUserId(loggedInUser.getUserId());

       if (existingProfile == null) {

           System.out.println("\n📝 Please complete your Job Seeker Profile");

           JobSeekerProfile profile = new JobSeekerProfile();
           profile.setUserId(loggedInUser.getUserId());

           System.out.print("Full Name: ");
           profile.setFullName(sc.nextLine());

           System.out.print("Phone: ");
           profile.setPhone(sc.nextLine());

           System.out.print("Location: ");
           profile.setLocation(sc.nextLine());

           System.out.print("Total Experience (years): ");
           profile.setTotalExperience(Integer.parseInt(sc.nextLine()));

           boolean created = profileService.createProfile(profile);

           if (created) {
               System.out.println("✅ Profile created successfully!");
           } else {
               System.out.println("❌ Failed to create profile.");
           }

       } else {
           System.out.println("✅ Profile already completed.");
       }

    // =================================================
    // RESUME CREATION (MANDATORY)
    // =================================================
    resume = resumeService.getResumeByUserId(loggedInUser.getUserId());

    if (resume == null) {
        Resume newResume = new Resume();
        newResume.setUserId(loggedInUser.getUserId());
        
        System.out.print("Enter your Career Objective: ");
        String objective = sc.nextLine();

        newResume.setObjective(objective);

        boolean resumeCreated = resumeService.createResume(newResume);

        if (resumeCreated) {
            resume = resumeService.getResumeByUserId(loggedInUser.getUserId());
            System.out.println("✅ Resume created successfully!");
        } else {
            System.out.println("❌ Failed to create resume.");
            return; // STOP further resume-dependent steps
        }
    }

       
       // =================================================
       // EDUCATION
       // =================================================
       System.out.print("\nWould you like to add education details? (Y/N): ");
       String eduChoice = sc.nextLine().trim().toUpperCase();

       if ("Y".equals(eduChoice)) {

           resume = resumeService.getResumeByUserId(loggedInUser.getUserId());

           if (resume == null) {
               System.out.println("❌ Resume not found. Cannot add education.");
           } else {

               boolean addMore = true;

               while (addMore) {

                   Education education = new Education();
                   education.setResumeId(resume.getResumeId());

                   System.out.print("Degree: ");
                   education.setDegree(sc.nextLine());

                   System.out.print("Institution: ");
                   education.setInstitution(sc.nextLine());

                   System.out.print("Start Year: ");
                   education.setStartYear(Integer.parseInt(sc.nextLine()));

                   System.out.print("End Year: ");
                   education.setEndYear(Integer.parseInt(sc.nextLine()));

                   boolean added = educationService.addEducation(education);

                   if (added) {
                       System.out.println("✅ Education added successfully!");
                   } else {
                       System.out.println("❌ Failed to add education.");
                   }

                   System.out.print("\nAdd another education? (Y/N): ");
                   addMore = "Y".equals(sc.nextLine().trim().toUpperCase());
               }
           }
       }

       // =================================================
       // EXPERIENCE
       // =================================================
       System.out.print("\nWould you like to add experience details? (Y/N): ");
       String expChoice = sc.nextLine().trim();

       if ("Y".equalsIgnoreCase(expChoice)) {

           boolean addMoreExp = true;

           while (addMoreExp) {

               Experience experience = new Experience();
               experience.setResumeId(resume.getResumeId());

               System.out.print("Company: ");
               experience.setCompany(sc.nextLine());

               System.out.print("Job Role: ");
               experience.setJobRole(sc.nextLine());

               System.out.print("Start Date (yyyy-mm-dd): ");
               experience.setStartDate(Date.valueOf(sc.nextLine()));

               System.out.print("End Date (yyyy-mm-dd or press Enter if current): ");
               String endDateInput = sc.nextLine();
               if (!endDateInput.trim().isEmpty()) {
                   experience.setEndDate(Date.valueOf(endDateInput));
               } else {
                   experience.setEndDate(null);
               }

               System.out.print("Description: ");
               experience.setDescription(sc.nextLine());

               boolean added = experienceService.addExperience(experience);

               if (added) {
                   System.out.println("✅ Experience added successfully!");
               } else {
                   System.out.println("❌ Failed to add experience.");
               }

               System.out.print("\nAdd another experience? (Y/N): ");
               addMoreExp = "Y".equalsIgnoreCase(sc.nextLine().trim());
           }
       }

       // =================================================
       // PROJECTS
       // =================================================
       System.out.print("\nWould you like to add project details? (Y/N): ");
       String projChoice = sc.nextLine().trim();

       if ("Y".equalsIgnoreCase(projChoice)) {

           boolean addMoreProjects = true;

           while (addMoreProjects) {

               Project project = new Project();
               project.setResumeId(resume.getResumeId());

               System.out.print("Project Title: ");
               project.setTitle(sc.nextLine());

               System.out.print("Project Description: ");
               project.setDescription(sc.nextLine());

               System.out.print("Tech Stack (comma separated): ");
               project.setTechStack(sc.nextLine());

               boolean added = projectService.addProject(project);

               if (added) {
                   System.out.println("✅ Project added successfully!");
               } else {
                   System.out.println("❌ Failed to add project.");
               }

               System.out.print("\nAdd another project? (Y/N): ");
               addMoreProjects = "Y".equalsIgnoreCase(sc.nextLine().trim());
           }
       }

       // =================================================
       // SKILLS
       // =================================================
       System.out.print("\nWould you like to add skills? (Y/N): ");
       String skillChoice = sc.nextLine().trim();

       if ("Y".equalsIgnoreCase(skillChoice)) {

           boolean addMoreSkills = true;

           while (addMoreSkills) {

               System.out.print("Enter Skill Name: ");
               String skillName = sc.nextLine();

               boolean added =
                       skillService.addSkillToResume(resume.getResumeId(), skillName);

               if (added) {
                   System.out.println("✅ Skill added successfully!");
               } else {
                   System.out.println("⚠️ Skill already exists or failed to add.");
               }

               System.out.print("\nAdd another skill? (Y/N): ");
               addMoreSkills = "Y".equalsIgnoreCase(sc.nextLine().trim());
           }
       }

       // =================================================
       // JOB SEARCH & APPLY
       // =================================================
       System.out.print("\nWould you like to search and apply for jobs? (Y/N): ");
       String applyChoice = sc.nextLine().trim().toUpperCase();

       if ("Y".equals(applyChoice)) {

           JobService jobService = new JobServiceImpl();
           JobApplicationService applicationService =
                   new JobApplicationServiceImpl();

           System.out.println("\n===== AVAILABLE JOBS =====");

           List<Job> jobs = jobService.getAllOpenJobs();

           if (jobs.isEmpty()) {
               System.out.println("📭 No open jobs available.");
           } else
                   jobs.forEach(j ->
                           System.out.println(
                                   j.getJobId() + " | " +
                                   j.getTitle() + " | " +
                                   j.getLocation() + " | " +
                                   j.getJobType()
                           )
                   );
       
           System.out.print("\nEnter Job ID to apply: ");
           int jobId = Integer.parseInt(sc.nextLine());

           Job selectedJob = jobService.viewJobDetails(jobId);

           if (selectedJob == null ||
                   !"OPEN".equalsIgnoreCase(selectedJob.getStatus())) {

               System.out.println("❌ Invalid or closed job.");
           } else {

               JobApplication application = new JobApplication();
               application.setJobId(jobId);
               application.setUserId(loggedInUser.getUserId());

               System.out.print("Enter Cover Letter (optional): ");
               application.setCoverLetter(sc.nextLine());

               boolean applied =
                       applicationService.applyJob(application);

               if (applied) {
                   System.out.println("✅ Job applied successfully!");
               } else {
                   System.out.println("❌ Failed to apply for job.");
               }

               Notification notification = new Notification();
               notification.setUserId(loggedInUser.getUserId());
               notification.setMessage(
                       "You have successfully applied for Job ID: " + jobId
               );
               notification.setRead(false);
               notification.setCreatedAt(
                       new Timestamp(System.currentTimeMillis())
               );

               notificationService.sendNotification(notification);
           }
           System.out.print("\nWould you like to view your applications? (Y/N): ");
           String viewChoice = sc.nextLine().trim().toUpperCase();

           if ("Y".equals(viewChoice)) {

               JobApplicationService jobApplicationService =
                       new JobApplicationServiceImpl();

               List<JobApplication> applications =
                       applicationService.viewMyApplications(loggedInUser.getUserId());

               if (applications.isEmpty()) {
                   System.out.println("📭 No applications found.");
               } else {
                   System.out.println("\n===== MY APPLICATIONS =====");
                   System.out.println("ID | Job Title | Location | Status");

                   for (JobApplication app : applications) {
                       System.out.println(
                           app.getApplicationId() + " | " +
                           app.getJobId() + " | " +
                           app.getJobId() + " | " +
                           app.getStatus()
                       );
                   }
               }        
           }
           System.out.print("\nWould you like to withdraw an application? (Y/N): ");
           String withdrawChoice = sc.nextLine().trim().toUpperCase();

           if ("Y".equals(withdrawChoice)) {

               System.out.print("Enter Application ID: ");
               int applicationId = Integer.parseInt(sc.nextLine());

               System.out.print("Reason (optional): ");
               String reason = sc.nextLine();

               boolean withdrawn =
                       applicationService.withdrawApplication(applicationId, reason);

               if (withdrawn) {
                   System.out.println("✅ Application withdrawn successfully.");
               } else {
                   System.out.println("❌ Failed to withdraw application.");
               }
           }

       }
   }
   else if ("EMPLOYER".equals(loggedInUser.getRole())) {

	    // =========================================
	    // EMPLOYER PROFILE (ONCE)
	    // =========================================
	    EmployerProfile existingProfile =
	            employerProfileService.getProfileByUserId(loggedInUser.getUserId());

	    if (existingProfile == null) {

	        System.out.println("\n🏢 Please complete your Employer Profile");

	        EmployerProfile profile = new EmployerProfile();
	        profile.setUserId(loggedInUser.getUserId());

	        System.out.print("Company Name: ");
	        profile.setCompanyName(sc.nextLine());

	        System.out.print("Industry: ");
	        profile.setIndustry(sc.nextLine());

	        System.out.print("Company Size: ");
	        profile.setCompanySize(sc.nextLine());

	        System.out.print("Company Description: ");
	        profile.setDescription(sc.nextLine());

	        System.out.print("Website: ");
	        profile.setWebsite(sc.nextLine());

	        System.out.print("Location: ");
	        profile.setLocation(sc.nextLine());

	        boolean created = employerProfileService.createProfile(profile);

	        if (!created) {
	            System.out.println("❌ Failed to create employer profile.");
	            return;
	        }

	        System.out.println("✅ Employer profile created successfully!");
	    }

	    // =========================================
	    // EMPLOYER DASHBOARD LOOP ✅
	    // =========================================
	    boolean logout = false;
	    JobService jobService = new JobServiceImpl();

	    while (!logout) {

	        System.out.println("\n===== EMPLOYER DASHBOARD =====");
	        System.out.println("1. Post Job");
	        System.out.println("2. View My Jobs");
	        System.out.println("3. Logout");
	        System.out.print("Choose option: ");
	        
	        String input = sc.nextLine();
	        
	        if(input.trim().isEmpty()) {
	        	System.out.println("❌ Please enter a number.");
	        	continue;
	        }
	        int choice;
	        try {
	            choice = Integer.parseInt(input);
	        } catch (NumberFormatException e) {
	            System.out.println("❌ Please enter a valid number (1-3).");
	            continue;
	        }

	        
	        switch (choice) {

	            case 1:
	                // 👉 PASTE YOUR EXISTING POST-JOB CODE HERE
	            	Job job = new Job();
	            	job.setEmployerId(loggedInUser.getUserId());

	            	System.out.print("Job Title: ");
	            	job.setTitle(sc.nextLine());

	            	System.out.print("Job Description: ");
	            	job.setDescription(sc.nextLine());

	            	System.out.print("Experience Required (years): ");
	            	job.setExperienceRequired(Integer.parseInt(sc.nextLine()));

	            	System.out.print("Education Required: ");
	            	job.setEducationRequired(sc.nextLine());

	            	System.out.print("Location: ");
	            	job.setLocation(sc.nextLine());

	            	System.out.print("Salary Min: ");
	            	job.setSalaryMin(Double.parseDouble(sc.nextLine()));

	            	System.out.print("Salary Max: ");
	            	job.setSalaryMax(Double.parseDouble(sc.nextLine()));

	            	System.out.print("Job Type (FULL_TIME / PART_TIME / INTERNSHIP / CONTRACT): ");
	            	job.setJobType(sc.nextLine().toUpperCase());

	            	System.out.print("Deadline (yyyy-mm-dd): ");
	            	job.setDeadline(Date.valueOf(sc.nextLine()));

	            	jobService.createJob(job);
	            	System.out.println("✅ Job posted successfully!");

	                break;

	            case 2:
	                
	                System.out.println("\n===== MY JOBS =====");

	                List<Job> jobs = jobService.viewMyJobs(loggedInUser.getUserId());

	                if (jobs == null || jobs.isEmpty()) {
	                    System.out.println("⚠️ You have not posted any jobs yet.");
	                } else {
	                    for (Job j : jobs) {
	                        System.out.println(
	                            "Job ID      : " + j.getJobId() +
	                            "\nTitle       : " + j.getTitle() +
	                            "\nLocation    : " + j.getLocation() +
	                            "\nJob Type    : " + j.getJobType() +
	                            "\nStatus      : " + j.getStatus() +
	                            "\nDeadline    : " + j.getDeadline() +
	                            "\n-----------------------------"
	                        );
	                    }
	                }
	                break;
	             
	            case 3:
	                logout = true;
	                System.out.println("Logged out successfully.");
	                break;

	            default:
	            	System.out.println("❌ Invalid choice.");
	            	System.out.println("DEBUG choice = " + choice);

	        }
	    }
	}
    sc.close();
}
}