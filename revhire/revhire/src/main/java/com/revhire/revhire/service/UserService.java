package com.revhire.revhire.service;

import com.revhire.revhire.modals.User;

public interface UserService {

    String registerUser(User user);

    User login(String email, String password);

    String getSecurityQuestion(String email);

    boolean verifySecurityAnswer(String email, String answer);

    String resetPassword(String email, String newPassword);
}

