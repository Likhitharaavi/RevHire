package com.revhire.revhire;

import java.sql.Connection;

import com.revhire.revhire.config.DBConnection;
import com.revhire.revhire.config.DatabaseInitializer;

public class App {

    public static void main(String[] args) {

        System.out.println("Application started...");

        try (Connection connection = DBConnection.getConnection()) {
         
            DatabaseInitializer.runScriptOnce(connection);
        	
            System.out.println("✅ Successfully connected to the database!");

            // Project logic here
            // service → dao → db

        } catch (Exception e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
    }
}
