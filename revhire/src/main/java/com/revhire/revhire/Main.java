package com.revhire.revhire;

import java.util.Scanner;
import java.util.List;
import com.revhire.revhire.modals.Notification;
import com.revhire.revhire.service.NotificationService;
import com.revhire.revhire.service.implementation.NotificationServiceImpl;
import java.sql.Timestamp;
//import com.revhire.revhire.service.JobSkillService;
//import com.revhire.revhire.service.implementation.JobSkillServiceImpl;
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
   
  private static NotificationService notificationService = new NotificationServiceImpl();
//  private static JobApplicationService applicationService = new JobApplicationServiceImpl();
  
  public static void main(String[] args) {

      Scanner sc = new Scanner(System.in);
      
      UserService userService = new UserServiceImpl();
//      JobSeekerProfileService profileService = new JobSeekerProfileServiceImpl();
//      ResumeService resumeService = new ResumeServiceImpl();
//      EducationService educationService = new EducationServiceImpl();
//      ExperienceService experienceService = new ExperienceServiceImpl();
//      ProjectService projectService = new ProjectServiceImpl();
//      SkillService skillService = new SkillServiceImpl();
//      EmployerProfileService employerProfileService = new EmployerProfileServiceImpl();
//      JobSkillService jobSkillService = new JobSkillServiceImpl();   
//      JobService jobService = new JobServiceImpl();
      

              while (true) {

                  System.out.println("\n===== REVHIRE =====");
                  System.out.println("1. Register");
                  System.out.println("2. Login");
                  System.out.println("3. Forgot Password");
                  System.out.println("4. Exit");
                  System.out.print("Choose option: ");

                  String choice = sc.nextLine();

                  switch (choice) {

                      case "1":
                          register(sc, userService);
                          break;

                      case "2":
                          login(sc, userService);
                          break;

                      case "3":
                          handleForgotPassword(sc, userService);
                          break;

                      case "4":
                          System.out.println("Goodbye!");
                          return;

                      default:
                          System.out.println("❌ Invalid choice.");
                  }
              }
          }

          // ================= REGISTER =================
          private static void register(Scanner sc, UserService userService) {

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

              if ("SUCCESS".equals(result)) {
                  System.out.println("✅ Registration successful!");
              } else if ("EMAIL_EXISTS".equals(result)) {
                  System.out.println("⚠️ Email already registered.");
              } else {
                  System.out.println("❌ Registration failed.");
              }
          }

          // ================= LOGIN =================
          private static void login(Scanner sc, UserService userService) {

              System.out.print("Enter Email: ");
              String email = sc.nextLine();

              System.out.print("Enter Password: ");
              String password = sc.nextLine();

              User loggedInUser = userService.login(email, password);

              if (loggedInUser == null) {
                  System.out.println("❌ Invalid credentials.");
                  return;
              }
              
              System.out.println("✅ Login successful!");

              if ("JOB_SEEKER".equals(loggedInUser.getRole())) {
                  runJobSeekerFlow(sc, loggedInUser);
              } else if ("EMPLOYER".equals(loggedInUser.getRole())) {
                  runEmployerFlow(sc, loggedInUser);
              } else {
                  System.out.println("❌ Unknown role.");
              }
          }
          
          // ================= FORGOT PASSWORD =================
          private static void handleForgotPassword(Scanner sc, UserService userService) {
          
          System.out.println("\n===== FORGOT PASSWORD =====");

          System.out.print("Enter registered email: ");
          String email = sc.nextLine();

          // 1. Get security question
          String question = userService.getSecurityQuestion(email);

          if (question == null || question.equals("User not found.")) {
              System.out.println("❌ Email not found.");
              return;
          }

          System.out.println("Security Question:");
          System.out.println(question);

          // 2. Validate answer
          System.out.print("Enter Security Answer: ");
          String answer = sc.nextLine();

          boolean isValid = userService.validateSecurityAnswer(email, answer);

          if (!isValid) {
              System.out.println("❌ Incorrect answer.");
              return;
          }

          // 3. Update password
          System.out.print("Enter new password: ");
          String newPassword = sc.nextLine();

          boolean updated = userService.updatePassword(email, newPassword);

          if (updated) {
              System.out.println("✅ Password reset successful.");
          } else {
              System.out.println("❌ Failed to reset password.");
          }

          }
          // ================= JOB SEEKER =================
          private static void runJobSeekerFlow(Scanner sc, User loggedInUser) {

        	    boolean logout = false;

        	    while (!logout) {
        	    	
        	    	int unreadCount = notificationService
        	    	        .countUnreadByUserId(loggedInUser.getUserId());
        	    	
        	        System.out.println("\n===== JOB SEEKER DASHBOARD =====");
        	        System.out.println("1. Complete / View Profile");
        	        System.out.println("2. Create / Update Resume");
        	        System.out.println("3. Search Jobs");
        	        System.out.println("4. Apply for Job");
        	        System.out.println("5. View My Applications");
        	        System.out.println("6. Withdraw Application");
        	        System.out.println("7. Notification (" + unreadCount + "NEW)");
        	        System.out.println("8. Logout");
        	        System.out.print("Choose option: ");

        	        String choice = sc.nextLine();

        	        switch (choice) {
        	        case "1": {
        	            JobSeekerProfileService profileService = new JobSeekerProfileServiceImpl();

        	            JobSeekerProfile profile =
        	                    profileService.getProfileByUserId(loggedInUser.getUserId());

        	            if (profile == null) {
        	                System.out.println("\n📝 Complete Your Profile");

        	                profile = new JobSeekerProfile();
        	                profile.setUserId(loggedInUser.getUserId());

        	                System.out.print("Full Name: ");
        	                profile.setFullName(sc.nextLine());

        	                System.out.print("Phone: ");
        	                profile.setPhone(sc.nextLine());

        	                System.out.print("Location: ");
        	                profile.setLocation(sc.nextLine());

        	                System.out.print("Total Experience (years): ");
        	                int experience = 0;

        	                while (true) {
        	                    System.out.print("Total Experience (years): ");
        	                    try {
        	                        experience = Integer.parseInt(sc.nextLine());
        	                        break;
        	                    } catch (NumberFormatException e) {
        	                        System.out.println("❌ Please enter numbers only.");
        	                    }
        	                }
                            profile.setTotalExperience(experience);
        	                
        	                boolean created = profileService.createProfile(profile);

        	                if (created) {
        	                    System.out.println("✅ Profile created successfully!");
        	                } else {
        	                    System.out.println("❌ Failed to create profile.");
        	                }
        	            } else {
        	                System.out.println("\n===== MY PROFILE =====");
        	                System.out.println("Name: " + profile.getFullName());
        	                System.out.println("Phone: " + profile.getPhone());
        	                System.out.println("Location: " + profile.getLocation());
        	                System.out.println("Experience: " + profile.getTotalExperience() + " years");
        	            }
        	            break;
        	        }
        	        case "2": {
        	            ResumeService resumeService = new ResumeServiceImpl();
        	            EducationService educationService = new EducationServiceImpl();
        	            ExperienceService experienceService = new ExperienceServiceImpl();
        	            SkillService skillService = new SkillServiceImpl();
        	            ProjectService projectService = new ProjectServiceImpl();

        	            Resume resume = resumeService.getResumeByUserId(loggedInUser.getUserId());

        	            if (resume == null) {
        	                Resume newResume = new Resume();
        	                newResume.setUserId(loggedInUser.getUserId());

        	                System.out.print("Enter Career Objective: ");
        	                newResume.setObjective(sc.nextLine());

        	                boolean created = resumeService.createResume(newResume);

        	                if (!created) {
        	                    System.out.println("❌ Failed to create resume.");
        	                    break;
        	                }
        	                resume = resumeService.getResumeByUserId(loggedInUser.getUserId());
        	                System.out.println("✅ Resume created.");
        	            }

        	            boolean back = false;

        	            while (!back) {
        	                System.out.println("\n===== RESUME MANAGEMENT =====");
        	                System.out.println("1. Update Career Objective");
        	                System.out.println("2. Add Education");
        	                System.out.println("2. Add Experience");
        	                System.out.println("3. Add Skills");
        	                System.out.println("4. Add Projects");
        	                System.out.println("5. Back");
        	                System.out.print("Choose option: ");

        	                String subChoice = sc.nextLine();

        	                switch (subChoice) {
        	                    // ===== CAREER OBJECTIVE =====
        	                    case "1":{
        	                    	System.out.print("Enter New Career Objective: ");
        	                    	String newObjective = sc.nextLine();

        	                    	resume.setObjective(newObjective);
        	                    	resumeService.updateResume(resume);

        	                    	System.out.println("✅ Career objective updated.");
        	                    	break;
        	                    }
        	                    //=======EDUCATION==============
        	                    case "2": {
        	                        Education edu = new Education();
        	                        edu.setResumeId(resume.getResumeId());

        	                        System.out.print("Degree: ");
        	                        edu.setDegree(sc.nextLine());

        	                        System.out.print("Institution: ");
        	                        edu.setInstitution(sc.nextLine());

        	                        System.out.print("Start Year: ");
        	                        edu.setStartYear(Integer.parseInt(sc.nextLine()));

        	                        System.out.print("End Year: ");
        	                        edu.setEndYear(Integer.parseInt(sc.nextLine()));

        	                        educationService.addEducation(edu);
        	                        System.out.println("✅ Education added.");
        	                        break;
        	                    }

        	                    // ===== EXPERIENCE =====
        	                    case "3": {
        	                        Experience exp = new Experience();
        	                        exp.setResumeId(resume.getResumeId());

        	                        System.out.print("Company: ");
        	                        exp.setCompany(sc.nextLine());

        	                        System.out.print("Job Role: ");
        	                        exp.setJobRole(sc.nextLine());

        	                        System.out.print("Start Date (yyyy-mm-dd): ");
        	                        exp.setStartDate(Date.valueOf(sc.nextLine()));

        	                        System.out.print("End Date (yyyy-mm-dd or blank if current): ");
        	                        String end = sc.nextLine();
        	                        exp.setEndDate(end.isEmpty() ? null : Date.valueOf(end));

        	                        System.out.print("Description: ");
        	                        exp.setDescription(sc.nextLine());

        	                        experienceService.addExperience(exp);
        	                        System.out.println("✅ Experience added.");
        	                        break;
        	                    }

        	                    // ===== SKILLS =====
        	                    case "4": {
        	                        System.out.print("Enter Skill: ");
        	                        String skillName = sc.nextLine();

        	                        boolean added =
        	                                skillService.addSkillToResume(resume.getResumeId(), skillName);

        	                        System.out.println(added
        	                                ? "✅ Skill added."
        	                                : "⚠️ Skill already exists.");
        	                        break;
        	                    }

        	                    // ===== PROJECTS =====
        	                    case "5": {
        	                        Project project = new Project();
        	                        project.setResumeId(resume.getResumeId());

        	                        System.out.print("Project Title: ");
        	                        project.setTitle(sc.nextLine());

        	                        System.out.print("Description: ");
        	                        project.setDescription(sc.nextLine());

        	                        System.out.print("Tech Stack: ");
        	                        project.setTechStack(sc.nextLine());

        	                        projectService.addProject(project);
        	                        System.out.println("✅ Project added.");
        	                        break;
        	                    }

        	                    case "6":
        	                        back = true;
        	                        break;

        	                    default:
        	                        System.out.println("❌ Invalid choice.");
        	                }
        	            }
        	            break;
        	        }
                        case "3": {
                            JobService jobService = new JobServiceImpl();

                            System.out.println("\n===== JOB SEARCH =====");
                            System.out.println("1. View All Jobs");
                            System.out.println("2. Search by Job Title");
                            System.out.println("3. Search by Location");
                            System.out.println("4. Search by Job Type");
                            System.out.print("Choose option: ");

                            String searchChoice = sc.nextLine();

                            List<Job> jobs = null;

                            switch (searchChoice) {

                                case "1":
                                    jobs = jobService.getAllOpenJobs();
                                    break;

                                case "2":
                                    System.out.print("Enter Job Title keyword: ");
                                    String title = sc.nextLine();
                                    jobs = jobService.searchJobsByTitle(title);
                                    break;

                                case "3":
                                    System.out.print("Enter Location: ");
                                    String location = sc.nextLine();
                                    jobs = jobService.searchJobsByLocation(location);
                                    break;

                                case "4":
                                    System.out.print("Enter Job Type (FULL_TIME / PART_TIME / INTERNSHIP / CONTRACT): ");
                                    String jobType = sc.nextLine().toUpperCase();
                                    jobs = jobService.searchJobsByJobType(jobType);
                                    break;

                                default:
                                    System.out.println("❌ Invalid option.");
                                    break;
                            }

                            if (jobs == null || jobs.isEmpty()) {
                                System.out.println("📭 No jobs found.");
                                break;
                            }

                            System.out.println("\n===== SEARCH RESULTS =====");
                            for (Job j : jobs) {
                                System.out.println(
                                    j.getJobId() + " | " +
                                    j.getTitle() + " | " +
                                    j.getLocation() + " | " +
                                    j.getJobType()
                                );
                            }
                            break;
                        }        	               

        	            case "4": {
        	                JobService jobService = new JobServiceImpl();
        	                JobApplicationService applicationService = new JobApplicationServiceImpl();
        	                NotificationService notificationService = new NotificationServiceImpl();

        	                System.out.println("\n===== AVAILABLE JOBS =====");
        	                List<Job> jobs = jobService.getAllOpenJobs();

        	                if (jobs.isEmpty()) {
        	                    System.out.println("📭 No open jobs available.");
        	                    break;
        	                }

        	                for (Job j : jobs) {
        	                    System.out.println(j.getJobId() + " | " + j.getTitle() + " | " + j.getLocation());
        	                }

        	                System.out.print("Enter Job ID to apply: ");
        	                int jobId = Integer.parseInt(sc.nextLine());

        	                Job selectedJob = jobService.viewJobDetails(jobId);

        	                if (selectedJob == null) {
        	                    System.out.println("❌ Invalid job.");
        	                    break;
        	                }

        	                JobApplication application = new JobApplication();
        	                application.setJobId(jobId);
        	                application.setUserId(loggedInUser.getUserId());

        	                System.out.print("Enter cover letter (optional): ");
        	                application.setCoverLetter(sc.nextLine());

        	                boolean applied = applicationService.applyJob(application);

        	                if (applied) {
        	                    System.out.println("✅ Job applied successfully!");

        	                    // Job Seeker notification
        	                    Notification jsNotification = new Notification();
        	                    jsNotification.setUserId(loggedInUser.getUserId());
        	                    jsNotification.setMessage("You applied for job: " + selectedJob.getTitle());
        	                    jsNotification.setRead(false);
        	                    jsNotification.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        	                    notificationService.sendNotification(jsNotification);

        	                    // Employer notification
        	                    Notification empNotification = new Notification();
        	                    empNotification.setUserId(selectedJob.getEmployerId());
        	                    empNotification.setMessage("New application received for job: " + selectedJob.getTitle());
        	                    empNotification.setRead(false);
        	                    empNotification.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        	                    notificationService.sendNotification(empNotification);
        	                } else {
        	                    System.out.println("❌ Failed to apply for job.");
        	                }
        	                break;
        	            }


        	            case "5": {
        	                JobApplicationService applicationService = new JobApplicationServiceImpl();

        	                List<JobApplication> applications =
        	                        applicationService.viewMyApplications(loggedInUser.getUserId());

        	                if (applications.isEmpty()) {
        	                    System.out.println("📭 No applications found.");
        	                    break;
        	                }

        	                System.out.println("\n===== MY APPLICATIONS =====");
        	                for (JobApplication app : applications) {
        	                    System.out.println(
        	                        "Application ID: " + app.getApplicationId() +
        	                        " | Job ID: " + app.getJobId() +
        	                        " | Status: " + app.getStatus()
        	                    );
        	                }
        	                break;
        	            }
        	            case "6": {
        	                JobApplicationService applicationService = new JobApplicationServiceImpl();

        	                System.out.print("Enter Application ID to withdraw: ");
        	                int applicationId = Integer.parseInt(sc.nextLine());

        	                System.out.print("Reason (optional): ");
        	                String reason = sc.nextLine();

        	                boolean withdrawn = applicationService.withdrawApplication(applicationId, reason);

        	                if (withdrawn) {
        	                    System.out.println("✅ Application withdrawn successfully.");
        	                } else {
        	                    System.out.println("❌ Failed to withdraw application.");
        	                }
        	                break;
        	            }
        	            case "7": {
        	                List<Notification> list =
        	                    notificationService.getNotificationsByUserId(loggedInUser.getUserId());

        	                if (list == null || list.isEmpty()) {
        	                    System.out.println("No notifications.");
        	                    break;
        	                }

        	                System.out.println("\n🔔 Your Notifications:");

        	                for (Notification n : list) {
        	                    System.out.println(
        	                        "ID: " + n.getNotificationId() +
        	                        " | " + (n.isRead() ? "[READ]" : "[UNREAD]") +
        	                        " | " + n.getMessage()
        	                    );
        	                }
                          while(true) {  
        	              
        	                int notifId = readInt(sc, "\nEnter Notification ID to mark as read (0 to go back): ");
        	                if (notifId == 0) 
        	                	break;
        	                    boolean updated = notificationService.markAsRead(notifId);

        	                    if (updated)
        	                        System.out.println("✅ Notification marked as read.");
        	                    else
        	                        System.out.println("⚠️ Already read or invalid notification ID.");
                          }

        	                break;
        	            }      	                          
        	            case "8":
        	                logout = true;
        	                System.out.println("Logged out from Job Seeker account.");
        	                break;
        	        
        	            default:
        	                System.out.println("❌ Invalid choice.");
        	        }
        	    }
        	}

          // ================= EMPLOYER =================
          private static void runEmployerFlow(Scanner sc, User loggedInUser)  {
        	    EmployerProfileService employerProfileService = new EmployerProfileServiceImpl();
        	    JobService jobService = new JobServiceImpl();
        	    JobApplicationService applicationService = new JobApplicationServiceImpl();
        	    NotificationService notificationService = new NotificationServiceImpl();

        	    EmployerProfile profile =
        	            employerProfileService.getProfileByUserId(loggedInUser.getUserId());

        	    if (profile == null) {
        	        System.out.println("\n=== CREATE EMPLOYER PROFILE ===");

        	        profile = new EmployerProfile();
        	        profile.setUserId(loggedInUser.getUserId());

        	        System.out.print("Company Name: ");
        	        profile.setCompanyName(sc.nextLine());

        	        System.out.print("Industry: ");
        	        profile.setIndustry(sc.nextLine());

        	        System.out.print("Company Size: ");
        	        profile.setCompanySize(sc.nextLine());

        	        System.out.print("Description: ");
        	        profile.setDescription(sc.nextLine());

        	        System.out.print("Website: ");
        	        profile.setWebsite(sc.nextLine());

        	        System.out.print("Location: ");
        	        profile.setLocation(sc.nextLine());

        	        employerProfileService.createProfile(profile);
        	        System.out.println("✅ Employer profile created.");
        	    }

        	    boolean logout = false;

        	    while (!logout) {
        	    	
        	    	int unreadCount = notificationService
        	    	        .countUnreadByUserId(loggedInUser.getUserId());

        	        System.out.println("\n===== EMPLOYER DASHBOARD =====");
        	        System.out.println("1. Post Job");
        	        System.out.println("2. View My Jobs");
        	        System.out.println("3. Edit Job");
        	        System.out.println("4. View Applicants");
        	        System.out.println("5. Shortlist / Reject Applicant");
        	        System.out.println("6. Edit Employer Profile");
        	        System.out.println("7. View Notifications (" + unreadCount + "NEW)");
        	        System.out.println("8. Close / Reopen Job" ); 
        	        System.out.println("9. Logout");
        	        System.out.print("Choose option: ");

        	        String choice = sc.nextLine();

        	        switch (choice) {

        	            case "1": {
        	                Job job = new Job();
        	                job.setEmployerId(loggedInUser.getUserId());

        	                System.out.print("Job Title: ");
        	                job.setTitle(sc.nextLine());

        	                System.out.print("Description: ");
        	                job.setDescription(sc.nextLine());

        	                System.out.print("Experience Required: ");
        	                int experience = 0;

        	                while (true) {
        	                    System.out.print("Experience Required (in years): ");
        	                    try {
        	                        experience = Integer.parseInt(sc.nextLine());
        	                        break;
        	                    } catch (NumberFormatException e) {
        	                        System.out.println("❌ Invalid input. Enter numbers only.");
        	                    }
        	                }
                            job.setExperienceRequired(experience);
        	                
        	                System.out.print("Education Required: ");
        	                job.setEducationRequired(sc.nextLine());

        	                System.out.print("Location: ");
        	                job.setLocation(sc.nextLine());

        	                System.out.print("Salary Min: ");
        	                double salaryMin = 0;

        	                while (true) {
        	                    System.out.print("Salary Min: ");
        	                    try {
        	                        salaryMin = Double.parseDouble(sc.nextLine());
        	                        break;
        	                    } catch (NumberFormatException e) {
        	                        System.out.println("❌ Please enter numbers only for Salary Min.");
        	                    }
        	                }
                            job.setSalaryMin(salaryMin);
        	                
        	                System.out.print("Salary Max: ");
        	                double salaryMax = 0;

        	                while (true) {
        	                    System.out.print("Salary Max: ");
        	                    try {
        	                        salaryMax = Double.parseDouble(sc.nextLine());
        	                        break;
        	                    } catch (NumberFormatException e) {
        	                        System.out.println("❌ Please enter numbers only for Salary Max.");
        	                    }
        	                }
                            job.setSalaryMax(salaryMax);
        	                
        	                System.out.print("Job Type: ");
        	                job.setJobType(sc.nextLine().toUpperCase());

        	                System.out.print("Deadline (yyyy-mm-dd): ");
        	                job.setDeadline(Date.valueOf(sc.nextLine()));

        	                jobService.createJob(job);
        	                System.out.println("✅ Job posted.");
        	                break;
        	            }

        	            case "2": {
        	                List<Job> jobs = jobService.viewMyJobs(loggedInUser.getUserId());

        	                if (jobs.isEmpty()) {
        	                    System.out.println("No jobs found.");
        	                    break;
        	                }

        	                for (Job j : jobs) {
        	                    System.out.println("\n=========AVAILABLE JOBS=========\n");
        	                    System.out.println("ID: " + j.getJobId());
        	                    System.out.println("Title: " + j.getTitle());
        	                    System.out.println("Status: " + j.getStatus());
        	                    System.out.println("===================================\n");
        	                }
        	                break;
        	            }

        	            case "3": {
        	                System.out.print("Enter Job ID: ");
        	                int jobId = Integer.parseInt(sc.nextLine());

        	                Job job = jobService.getJobById(jobId);

        	                if (job == null || job.getEmployerId() != loggedInUser.getUserId()) {
        	                    System.out.println("Invalid job.");
        	                    break;
        	                }

        	                System.out.print("New Title (" + job.getTitle() + "): ");
        	                String t = sc.nextLine();
        	                if (!t.isEmpty()) job.setTitle(t);

        	                System.out.print("New Description (" + job.getDescription() + "): ");
        	                String d = sc.nextLine();
        	                if (!d.isEmpty()) job.setDescription(d);

        	                System.out.print("New Location (" + job.getLocation() + "): ");
        	                String l = sc.nextLine();
        	                if (!l.isEmpty()) job.setLocation(l);

        	                System.out.print("New Salary Min (" + job.getSalaryMin() + "): ");
        	                String min = sc.nextLine();
        	                if (!min.isEmpty()) job.setSalaryMin(Double.parseDouble(min));

        	                System.out.print("New Salary Max (" + job.getSalaryMax() + "): ");
        	                String max = sc.nextLine();
        	                if (!max.isEmpty()) job.setSalaryMax(Double.parseDouble(max));

        	                jobService.createJob(job);
        	                System.out.println("✅ Job updated.");
        	                break;
        	            }

        	            case "4": {

        	                // 1️⃣ First show employer jobs
        	                List<Job> jobs = jobService.viewMyJobs(loggedInUser.getUserId());

        	                if (jobs.isEmpty()) {
        	                    System.out.println("No jobs found.");
        	                    break;
        	                }

        	                System.out.println("\n===== YOUR JOBS =====\n");

        	                for (Job j : jobs) {
        	                    System.out.println(
        	                        "Job ID: " + j.getJobId() +
        	                        " | Title: " + j.getTitle()
        	                    );
        	                }

        	                // 2️⃣ Ask which job
        	                System.out.print("\nEnter Job ID to view applicants: ");
        	                int jobId = Integer.parseInt(sc.nextLine());

        	                List<JobApplication> apps =
        	                        applicationService.viewApplicationsForJob(jobId);

        	                if (apps.isEmpty()) {
        	                    System.out.println("No applicants for this job.");
        	                    break;
        	                }

        	                // 3️⃣ Show applicants
        	                System.out.println("\n===== APPLICANTS =====\n");

        	                for (JobApplication app : apps) {
        	                    System.out.println("--------------------------------");
        	                    System.out.println("Application ID : " + app.getApplicationId());
        	                    System.out.println("Candidate ID   : " + app.getUserId());
        	                    System.out.println("Status         : " + app.getStatus());
        	                    System.out.println("Applied on     : " + app.getAppliedAt());
        	                    System.out.println("--------------------------------\n");
        	                }

        	                // 4️⃣ Optional update
        	                System.out.print("Enter Application ID to update status (0 to skip): ");
        	                int appId = Integer.parseInt(sc.nextLine());

        	                if (appId != 0) {
        	                    System.out.print("Enter new status (SHORTLISTED / REJECTED): ");
        	                    String status = sc.nextLine().toUpperCase();

        	                    applicationService.updateApplicationStatus(appId, status);
        	                    System.out.println("✅ Application status updated.");
        	                }
        	                    break;

        	                }   

        	            case "5": {
        	                System.out.print("Application ID: ");
        	                int appId = Integer.parseInt(sc.nextLine());

        	                System.out.print("1.Shortlist  2.Reject: ");
        	                String act = sc.nextLine();

        	                String status = act.equals("1") ? "SHORTLISTED" : "REJECTED";

        	                applicationService.updateApplicationStatus(
        	                        appId,
        	                        status,
        	                        "Updated by employer"
        	                );

        	                System.out.println("✅ Application updated.");
        	                break;
        	            }

        	            case "6": {
        	                EmployerProfile p =
        	                        employerProfileService.getProfileByUserId(loggedInUser.getUserId());

        	                System.out.print("New Company Name (" + p.getCompanyName() + "): ");
        	                String n = sc.nextLine();
        	                if (!n.isEmpty()) p.setCompanyName(n);

        	                System.out.print("New Industry (" + p.getIndustry() + "): ");
        	                String i = sc.nextLine();
        	                if (!i.isEmpty()) p.setIndustry(i);

        	                System.out.print("New Location (" + p.getLocation() + "): ");
        	                String loc = sc.nextLine();
        	                if (!loc.isEmpty()) p.setLocation(loc);

        	                employerProfileService.updateProfile(p);
        	                System.out.println("✅ Profile updated.");
        	                break;
        	            }

        	            case "7": {
        	                
        	                List<Notification> notes =
        	                        notificationService.getNotificationsByUserId(
        	                                loggedInUser.getUserId());

        	                if (notes == null || notes.isEmpty()) {
        	                    System.out.println("No notifications.");
        	                    break;
        	                }

        	                System.out.println("\n🔔 Employer Notifications:\n");

        	                for (Notification n : notes) {
        	                    System.out.println(
        	                            "ID: " + n.getNotificationId() +
        	                            " | " + (n.isRead() ? "[READ]" : "[UNREAD]") +
        	                            " | " + n.getMessage()
        	                    );
        	                }

        	                while (true) {

        	                    int notifId = readInt(sc,
        	                            "\nEnter Notification ID to mark as read (0 to go back): ");

        	                    if (notifId == 0)
        	                        break;

        	                    boolean updated = notificationService.markAsRead(notifId);

        	                    if (updated)
        	                        System.out.println("✅ Notification marked as read.");
        	                    else
        	                        System.out.println("⚠️ Already read or invalid ID.");
        	                }

        	                break;
        	            }
        	            case "8":{

        	                List<Job> jobs = jobService.viewMyJobs(loggedInUser.getUserId());

        	                if (jobs == null || jobs.isEmpty()) {
        	                    System.out.println("No jobs found.");
        	                    break;
        	                }

        	                System.out.println("\n===== YOUR JOBS =====");

        	                for (Job j : jobs) {
        	                    System.out.println(
        	                        "ID: " + j.getJobId() +
        	                        " | Title: " + j.getTitle() +
        	                        " | Status: " + j.getStatus()
        	                    );
        	                }

        	                int jobId = readInt(sc, "\nEnter Job ID to update: ");

        	                Job job = jobService.getJobById(jobId);

        	                if (job == null || job.getEmployerId() != loggedInUser.getUserId()) {
        	                    System.out.println("❌ Invalid job ID.");
        	                    break;
        	                }

        	                String currentStatus = job.getStatus();
        	                System.out.println("Current Status: " + currentStatus);

        	                if ("OPEN".equalsIgnoreCase(currentStatus)) {

        	                    System.out.print("Do you want to CLOSE this job? (Y/N): ");
        	                    if (sc.nextLine().equalsIgnoreCase("Y")) {

        	                        boolean updated = jobService.updateJobStatus(jobId, "CLOSED");

        	                        if (updated)
        	                            System.out.println("✅ Job closed successfully.");
        	                        else
        	                            System.out.println("❌ Failed to update job.");
        	                    }

        	                } else if ("CLOSED".equalsIgnoreCase(currentStatus)) {

        	                    System.out.print("Do you want to REOPEN this job? (Y/N): ");
        	                    if (sc.nextLine().equalsIgnoreCase("Y")) {

        	                        boolean updated = jobService.updateJobStatus(jobId, "OPEN");

        	                        if (updated)
        	                            System.out.println("✅ Job reopened successfully.");
        	                        else
        	                            System.out.println("❌ Failed to update job.");
        	                    }

        	                } else {
        	                    System.out.println("⚠️ Unknown job status.");
        	                }

        	                break;
        	            }
        	            case "9":
        	                logout = true;
        	                System.out.println("Logged out.");
        	                break;

        	            default:
        	                System.out.println("Invalid choice.");
        	    }
        	    }
          }
        	    // 👇 ADD readInt METHOD HERE (BEFORE FINAL })

          private static int readInt(Scanner sc, String message) {

        	      while (true) {
        	          System.out.print(message);
        	          try {
        	              return Integer.parseInt(sc.nextLine());
        	         } catch (NumberFormatException e) {
        	              System.out.println("❌ Please enter numbers only.");
        	          }
        	      }

          }
          }