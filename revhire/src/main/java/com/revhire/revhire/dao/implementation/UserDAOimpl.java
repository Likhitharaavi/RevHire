package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.UserDAO;
import com.revhire.revhire.modals.User;

public class UserDAOimpl implements UserDAO 
{

	@Override
	public boolean registerUser(User user) {
		String sql = "INSERT INTO users (email, password, role, security_question, security_answer) VALUES (?,?,?,?,?)";
//		? are placeholders, not usually directly filled with strings. To give state the preparedstatement is used
//		establishing database connection
		try (Connection conn = DBConnection.getConnection(); 
//		pre-compiled sql statement that prevents SQL injection by treating user input as data.
				
//				binding data to placeholders
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1,  user.getEmail());
			stmt.setString(2,  user.getPassword());
			stmt.setString(3,  user.getRole());
			stmt.setString(4,  user.getSecurityQuestion());
			stmt.setString(5,  user.getSecurityAnswer());
			
//			executeUpdate is used for insert, update, and delete, return a number of rows changed.
			int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
    
    // ✅ NEW METHOD (REQUIRED FOR EMAIL EXISTENCE CHECK)
    @Override
    public User getUserByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    user.setProfileCompletionPercent(rs.getInt("profile_completion_percent"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // user not found
    }
	
	@Override
	public User loginUser(String email, String password) {
		String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
		        
//		db connection
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        
//        executeQuery returns a ResultSet, a temporary table held in memory that contains the results of your search.
	        try (ResultSet rs = pstmt.executeQuery()) 
	        {
//	        to know who logged in the user details are stored in object User	
//	        the ResultSet when created, sits above the first row and on calling .next() moves the cursor down to first row of data. Till the method return true or null it moves.
	            if (rs.next()) 
	            {
	                User user = new User();
	                user.setUserId(rs.getInt("user_id"));
	                user.setEmail(rs.getString("email"));
	                user.setRole(rs.getString("role"));
	                user.setProfileCompletionPercent(rs.getInt("profile_completion_percent"));
	                return user;
	            }
	        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	        return null; // Login failed
	}
	
//	Forgot password

	@Override
	public String getSecurityQuestion(String email) 
	{
		String sql = "SELECT security_question FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next()) 
                {
                    return rs.getString("security_question");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
//        either email doesn't exist or sql exception occured.
        
	}

	@Override
	public boolean validateSecurityAnswer(String email, String answer) 
	{
		String sql = "SELECT 1 FROM users WHERE email = ? AND UPPER(security_answer) = UPPER(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            
            pstmt.setString(1, email);
            pstmt.setString(2, answer);
            
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                return rs.next(); // Returns true if a match is found
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
		    return false;
        }
	}
	
	@Override
	public boolean updatePassword(String email, String newPassword) 
	{
		String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
}