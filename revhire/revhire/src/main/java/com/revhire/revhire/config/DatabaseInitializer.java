package com.revhire.revhire.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseInitializer {

   public static void runScriptOnce(Connection conn) {
    try 
//    (Connection conn = DBConnection.getConnection()) 
    {
        // 1. Get ALL tables in your schema in ONE hit
        Set<String> actualTables = new HashSet<>();
        ResultSet rs = conn.getMetaData().getTables(null, "REV_USR", "%", new String[]{"TABLE"});
        while (rs.next()) {
            actualTables.add(rs.getString("TABLE_NAME").toUpperCase());
        }

        // 2. The list of tables your JD requires
        String[] expected = {"USERS", "JOBS", "PROJECTS", "CERTIFICATIONS", "JOB_APPLICATIONS"};
        
        boolean missingSomething = false;
        for (String table : expected) {
            if (!actualTables.contains(table)) {
                missingSomething = true;
                break;
            }
        }

        if (missingSomething) {
            executeSQL(conn);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private static void executeSQL(Connection conn) {
        // 2. Read schema.sql from src/main/resources
        try (InputStream is = DatabaseInitializer.class.getClassLoader().getResourceAsStream("schema.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is));
             Statement stmt = conn.createStatement()) {

            String sql = reader.lines().collect(Collectors.joining("\n"));
            
            // 3. Split by semicolon and execute each query
            String[] queries = sql.split(";");
            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    stmt.execute(query);
                }
            }
            System.out.println("All 14 RevHire tables created successfully!");

        } catch (Exception e) {
            System.err.println("Error executing schema script: " + e.getMessage());
        }
    }
}
