package com.revhire.revhire.service;

import com.revhire.revhire.modals.User;
import com.revhire.revhire.service.UserService;
//import com.revhire.revhire.service.implementation.UserServiceImpl;

public interface UserService {

    String registerUser(User user);

    User login(String email, String password);

    String getSecurityQuestion(String email);
  
    User getUserByEmail(String email); 
    
    boolean verifySecurityAnswer(String email, String answer);
    
    boolean updatePassword(String email, String newPassword);
    
    boolean validateSecurityAnswer(String email, String answer);

    String resetPassword(String email, String newPassword);
}

