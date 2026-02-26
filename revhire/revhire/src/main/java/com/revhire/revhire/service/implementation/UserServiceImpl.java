package com.revhire.revhire.service.implementation;

import com.revhire.revhire.dao.UserDAO;
import com.revhire.revhire.dao.implementation.UserDAOimpl;
import com.revhire.revhire.modals.User;
import com.revhire.revhire.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO = new UserDAOimpl();

    @Override
    public String registerUser(User user) {

        // 1. Validation logic
        if (user == null) {
            return "User data cannot be null.";
        }

        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            return "Invalid email address.";
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return "Password must be at least 6 characters.";
        }

        //EMAIL EXISTANCE CHECK(IMPORTANT)
        if (userDAO.getUserByEmail(user.getEmail()) !=null) {
        	return "EMAIL_EXIST";
        }
        
        // 2. Call DAO
        boolean isSaved = userDAO.registerUser(user);

        return isSaved
                ? "SUCCESS"
                : "FAILURE: Email might already be registered.";
    }

    @Override
    public User login(String email, String password) {

        // Business rule: don't hit DB if invalid input
        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            return null;
        }

        return userDAO.loginUser(email, password);
    }

    @Override
    public String getSecurityQuestion(String email) {

        if (email == null || email.trim().isEmpty()) {
            return "Email cannot be empty.";
        }

        String question = userDAO.getSecurityQuestion(email);
        return (question != null) ? question : "User not found.";
    }

    @Override
    public boolean verifySecurityAnswer(String email, String answer) {

        if (answer == null || answer.trim().isEmpty()) {
            return false;
        }

        return userDAO.validateSecurityAnswer(email, answer);
    }

    @Override
    public String resetPassword(String email, String newPassword) {

        if (newPassword == null || newPassword.length() < 6) {
            return "New password is too weak (min 6 characters).";
        }

        boolean isUpdated = userDAO.updatePassword(email, newPassword);

        return isUpdated
                ? "Password updated successfully!"
                : "Failed to update password.";
    }
}
