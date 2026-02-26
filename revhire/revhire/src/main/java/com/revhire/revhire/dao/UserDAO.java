package com.revhire.revhire.dao;

import com.revhire.revhire.modals.User;

public interface UserDAO 
{
	//checks if registration successfull
    boolean registerUser(User user);

    User getUserByEmail(String email);
    
    // credentials match, null otherwise
    User loginUser(String email, String password);

    // For forgot password logic
    String getSecurityQuestion(String email);
    boolean validateSecurityAnswer(String email, String answer);
    boolean updatePassword(String email, String newPassword);
}