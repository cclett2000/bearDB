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
import com.bearzwebworks.beardb.db.model.Contact;
import com.bearzwebworks.beardb.db.model.Project;
import com.bearzwebworks.beardb.globalVariables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class projectHandler {
    static final boolean DEBUG = false;

     /** add new project to project table */
    public static void addProject(Project projectData) {
        try {
            String statement = "INSERT INTO Project (ProjectName,CustomerID,HostingBeginDate,HostingEndDate,HostingPayment,WebDesignCost,DomainExpiration,URL,WordpressAddress,WordpressLogin,WordpressPassword,WooCommerceUser,WooCommercePass,WooCommerceSerial,isMonthly,isYearly) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = dbLogic.connect("ADD-PROJECT").prepareStatement(statement);

            preparedStatement.setString(1, projectData.getProjectName());
            preparedStatement.setInt(2, projectData.getCustomerID());
            preparedStatement.setString(3, projectData.getHostingBeginDate());
            preparedStatement.setString(4, projectData.getHostingEndDate());
            preparedStatement.setDouble(5, projectData.getHostingPayment());
            preparedStatement.setDouble(6, projectData.getWebDesignCost());
            preparedStatement.setString(7, projectData.getDomainExpiration());
            preparedStatement.setString(8, projectData.getURL());
            preparedStatement.setString(9, projectData.getWordpressAddress());
            preparedStatement.setString(10, projectData.getWordpressLogin());
            preparedStatement.setString(11, projectData.getWordpressPassword());
            preparedStatement.setString(12, projectData.getWooCommerceUser());
            preparedStatement.setString(13, projectData.getWordpressPassword());
            preparedStatement.setString(14, projectData.getWooCommerceSerial());
            preparedStatement.setInt(15, projectData.getIsMonthly());
            preparedStatement.setInt(16, projectData.getIsYearly());

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
            String statement = "UPDATE " + globalVariables.PROJECT_TABLE_NAME + " SET ProjectName=?, CustomerID=?, HostingBeginDate=?, HostingEndDate=?, HostingPayment=?, WebDesignCost=?, DomainExpiration=?, URL=?, WordpressAddress=?, WordpressLogin=?, WordpressPassword=?, WooCommerceUser=?, WooCommercePass=?, WooCommerceSerial=?, isMonthly=?, isYearly=? WHERE ProjectID = ?";

            PreparedStatement preparedStatement = dbLogic.connect("EDIT-PROJECT").prepareStatement(statement);

            preparedStatement.setString(1, p.getProjectName());
            preparedStatement.setInt(2, p.getCustomerID());
            preparedStatement.setString(3, p.getHostingBeginDate());
            preparedStatement.setString(4, p.getHostingEndDate());
            preparedStatement.setDouble(5, p.getHostingPayment());
            preparedStatement.setDouble(6, p.getWebDesignCost());
            preparedStatement.setString(7, p.getDomainExpiration());
            preparedStatement.setString(8, p.getURL());
            preparedStatement.setString(9, p.getWordpressAddress());
            preparedStatement.setString(10, p.getWordpressLogin());
            preparedStatement.setString(11, p.getWordpressPassword());
            preparedStatement.setString(12, p.getWooCommerceUser());
            preparedStatement.setString(12, p.getWooCommercePass());
            preparedStatement.setString(14, p.getWooCommerceSerial());
            preparedStatement.setInt(15, p.getIsMonthly());
            preparedStatement.setInt(16, p.getIsYearly());

            System.out.println("[DB-EDIT-PROJECT] - Editing Project.");
            preparedStatement.executeUpdate();
            System.out.println("[DB-EDIT-PROJECT]\t -- Done.");
        }
        catch (SQLException e){
            System.out.println("[DB-EDIT-PROJECT-ERR!] - " + e.getMessage());
            //e.printStackTrace();
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

                temp.setProjectName(rs.getString("ProjectName"));
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
