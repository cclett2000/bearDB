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

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class projectHandler {
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
}
