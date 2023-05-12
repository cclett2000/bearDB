/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
 *
 * =======BOILER PLATE FOR ADDING TO PROJECT TABLE========
 *         Project project = new Project();
 *
 *         project.setCustomerID(1);
 *         project.setProjectName("Name");
 *         project.setHostingBeginDate("BeginDate");
 *         project.setHostingEndDate("EndDate");
 *         project.setHostingPayment(1);
 *         project.setWebDesignCost(1);
 *         project.setDomainExpiration("ExpDate");
 *         project.setURL("URL");
 *         project.setWordpressAddress("WordAddy");
 *         project.setWordpressLogin("WordLoggy");
 *         project.setWordpressPassword("WordPassy");
 *         project.setWooCommerceUser("WooUser");
 *         project.setWooCommercePass("WooPassy");
 *         project.setWooCommerceSerial("WooSerial");
 *         project.setIsMonthly(0);
 *         project.setIsYearly(1);
 *
 *         projectHandler.addProject(project);
*/

package com.bearzwebworks.beardb.db.handler;

import com.bearzwebworks.beardb.db.dbLogic;
import com.bearzwebworks.beardb.db.model.Project;
import com.bearzwebworks.beardb.globalVariables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class projectHandler {
    static final boolean DEBUG = true;

     /** add new project to project table */
    public static void addProject(Project projectData) {
        try {
            String statement = "INSERT INTO Project (CustomerID,HostingBeginDate,HostingEndDate,HostingPayment,WebDesignCost,DomainExpiration,URL,WordpressAddress,WordpressLogin,WordpressPassword,WooCommerceUser,WooCommercePass,WooCommerceSerial,Monthly,Yearly) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = dbLogic.connect("ADD-PROJECT").prepareStatement(statement);

            preparedStatement.setInt(1, projectData.getCustomerID());
            preparedStatement.setString(2, projectData.getHostingBeginDate());
            preparedStatement.setString(3, projectData.getHostingEndDate());
            preparedStatement.setDouble(4, projectData.getHostingPayment());
            preparedStatement.setDouble(5, projectData.getWebDesignCost());
            preparedStatement.setString(6, projectData.getDomainExpiration());
            preparedStatement.setString(7, projectData.getURL());
            preparedStatement.setString(8, projectData.getWordpressAddress());
            preparedStatement.setString(9, projectData.getWordpressLogin());
            preparedStatement.setString(10, projectData.getWordpressPassword());
            preparedStatement.setString(11, projectData.getWooCommerceUser());
            preparedStatement.setString(12, projectData.getWordpressPassword());
            preparedStatement.setString(13, projectData.getWooCommerceSerial());
            preparedStatement.setInt(14, projectData.getIsMonthly());
            preparedStatement.setInt(15, projectData.getIsYearly());

            System.out.println("[DB-ADD-PROJECT] - Adding New Project - CustomerID=" +projectData.getCustomerID());
            preparedStatement.executeUpdate();
            System.out.println("[DB-ADD-PROJECT]\t -- Done.");
        }
        catch (SQLException e){
            System.out.println("[DB-ADD-PROJECT-ERR!] - " + e.getMessage());
            e.printStackTrace();
        }

    }

    /** update project information in project table */
    public static void updateProject(Project p) {
        try {
            String statement = "UPDATE " + globalVariables.PROJECT_TABLE_NAME + " SET CustomerID=?, HostingBeginDate=?, HostingEndDate=?, HostingPayment=?, WebDesignCost=?, DomainExpiration=?, URL=?, WordpressAddress=?, WordpressLogin=?, WordpressPassword=?, WooCommerceUser=?, WooCommercePass=?, WooCommerceSerial=?, Monthly=?, Yearly=? WHERE ProjectID = ?";

            PreparedStatement preparedStatement = dbLogic.connect("EDIT-PROJECT").prepareStatement(statement);

            preparedStatement.setInt(1, p.getCustomerID());
            preparedStatement.setString(2, p.getHostingBeginDate());
            preparedStatement.setString(3, p.getHostingEndDate());
            preparedStatement.setDouble(4, p.getHostingPayment());
            preparedStatement.setDouble(5, p.getWebDesignCost());
            preparedStatement.setString(6, p.getDomainExpiration());
            preparedStatement.setString(7, p.getURL());
            preparedStatement.setString(8, p.getWordpressAddress());
            preparedStatement.setString(9, p.getWordpressLogin());
            preparedStatement.setString(10, p.getWordpressPassword());
            preparedStatement.setString(11, p.getWooCommerceUser());
            preparedStatement.setString(12, p.getWooCommercePass());
            preparedStatement.setString(13, p.getWooCommerceSerial());
            preparedStatement.setInt(14, p.getIsMonthly());
            preparedStatement.setInt(15, p.getIsYearly());
            preparedStatement.setInt(16, p.getProjectID());

            System.out.println("[DB-EDIT-PROJECT] - Editing Project.");
            preparedStatement.executeUpdate();
            System.out.println("[DB-EDIT-PROJECT]\t -- Done.");
        }
        catch (SQLException e){
            System.out.println("[DB-EDIT-PROJECT-ERR!] - " + e.getMessage());
            if(DEBUG)
                e.printStackTrace();
        }

    }

    /** remove project from project table */
    public static void removeProject(int projectID){
        try (Connection conn = dbLogic.connect("PROJECT-DELETE");
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM " + globalVariables.PROJECT_TABLE_NAME + " WHERE ProjectID = " + projectID);

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** get all project info from table -- uses custom model to access/modify values */
    public static ObservableList<Project> getProjectData(int CustomerID){
        ObservableList<Project> rsData = FXCollections.observableArrayList();

        String getSize = "SELECT COUNT(*) FROM Project WHERE CustomerID = " + CustomerID;
        String sql = "SELECT * FROM Project WHERE CustomerID = " + CustomerID;

        try (Connection conn = dbLogic.connect("PROJECT-ALL-QUERY");
             Statement debugGetRowCount = conn.createStatement();
             Statement getDataStmt = conn.createStatement();
             ResultSet rs = getDataStmt.executeQuery(sql)){

            if(DEBUG) {
                int rowCount = debugGetRowCount.executeQuery(getSize).getInt(1);
                System.out.println("[PROJECT-ALL-QUERY] - Data Size: " + rowCount);
            } //endif

            // loop through the result set
            while(rs.next()){
                Project temp = new Project();

                temp.setProjectID(rs.getInt("ProjectID"));
                temp.setCustomerID(rs.getInt("CustomerID"));
                temp.setHostingBeginDate(rs.getString("HostingBeginDate"));
                temp.setHostingEndDate(rs.getString("HostingEndDate"));
                temp.setHostingPayment(rs.getDouble("HostingPayment"));
                temp.setWebDesignCost(rs.getDouble("WebDesignCost"));
                temp.setDomainExpiration(rs.getString("DomainExpiration"));
                temp.setURL(rs.getString("URL"));
                temp.setWordpressAddress(rs.getString("WordpressAddress"));
                temp.setWordpressLogin(rs.getString("WordpressLogin"));
                temp.setWordpressPassword(rs.getString("WordpressPassword"));
                temp.setWooCommerceUser(rs.getString("WooCommerceUser"));
                temp.setWooCommercePass(rs.getString("WooCommercePass"));
                temp.setWooCommerceSerial(rs.getString("WooCommerceSerial"));
                temp.setIsMonthly(rs.getInt("Monthly"));
                temp.setIsYearly(rs.getInt("Yearly"));

                rsData.add(temp);
            } //endloop
        }

        catch (SQLException e) {
            System.out.println("[PROJECT-ALL-QUERY-ERR!] - " + e.getMessage());
            e.printStackTrace();
        }
        return rsData;
    }
}
