package com.revhire.revhire.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.dao.ProjectDAO;
import com.revhire.revhire.modals.Project;

public class ProjectDAOImpl implements ProjectDAO {

    @Override
    public boolean addProject(Project project) {

        String sql = "INSERT INTO projects (resume_id, title, description, tech_stack) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, project.getResumeId());
            pstmt.setString(2, project.getTitle());
            pstmt.setString(3, project.getDescription());
            pstmt.setString(4, project.getTechStack());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Project> getProjectsByResumeId(int resumeId) {

        List<Project> list = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE resume_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, resumeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Project project = new Project();
                    project.setProjectId(rs.getInt("project_id"));
                    project.setResumeId(rs.getInt("resume_id"));
                    project.setTitle(rs.getString("title"));
                    project.setDescription(rs.getString("description"));
                    project.setTechStack(rs.getString("tech_stack"));
                    list.add(project);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
