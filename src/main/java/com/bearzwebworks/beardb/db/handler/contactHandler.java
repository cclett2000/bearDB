/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
 *
 * =======BOILER PLATE FOR ADDING TO CONTACT TABLE========
 *         Contact contact = new Contact();
 *
 *         contact.setCustomerID(419);
 *         contact.setContactTitle("Title");
 *         contact.setName("Name");
 *         contact.setEmailAddress("EmailAddy");
 *         contact.setEmailPass("EmailPassy");
 *         contact.setAlias("Alias");
 *         contact.setPhoneNumber("PhoneNumber");
 *         contact.setExtension("Extension");
 *         contact.setFaxNumber("FaxNumber");
 *         contact.setHomeNumber("HomeNumber");
 *         contact.setCellNumber("CellNumber");
 *         contact.setTollFree("TollFree");
 *
 *         contactHandler.addContact(contact);
*/


package com.bearzwebworks.beardb.db.handler;

import com.bearzwebworks.beardb.db.dbLogic;
import com.bearzwebworks.beardb.db.model.Contact;
import com.bearzwebworks.beardb.globalVariables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class contactHandler {
    static final boolean DEBUG = true;

    /** add new contact to contact table */
    public static void addContact(Contact contactData) {
        try {
            String statement = "INSERT INTO Contact (CustomerID,ContactTitle,Name,EmailAddress,EmailPass,Alias,PhoneNumber,Extension,FaxNumber,HomeNumber,CellNumber,TollFree) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = dbLogic.connect("ADD-CONTACT").prepareStatement(statement);

            preparedStatement.setInt(1, contactData.getCustomerID());
            preparedStatement.setString(2, contactData.getContactTitle());
            preparedStatement.setString(3, contactData.getName());
            preparedStatement.setString(4, contactData.getEmailAddress());
            preparedStatement.setString(5, contactData.getEmailPass());
            preparedStatement.setString(6, contactData.getAlias());
            preparedStatement.setString(7, contactData.getPhoneNumber());
            preparedStatement.setString(8, contactData.getExtension());
            preparedStatement.setString(9, contactData.getFaxNumber());
            preparedStatement.setString(10, contactData.getHomeNumber());
            preparedStatement.setString(11, contactData.getCellNumber());
            preparedStatement.setString(12, contactData.getTollFree());

            System.out.println("[DB-ADD-CONTACT] - Adding New Contact - CustomerID=" + contactData.getCustomerID());
            preparedStatement.executeUpdate();
            System.out.println("[DB-ADD-CONTACT]\t -- Done.");
        }
        catch (SQLException e){
            System.out.println("[DB-ADD-CONTACT-ERR!] - " + e.getMessage());
            e.printStackTrace();
        }

    }

    /** update contact information int contact table */
    public static void updateContact(Contact contact){
        try {
            String statement = "UPDATE " + globalVariables.CONTACT_TABLE_NAME + " SET ContactTitle = ?, Name = ?, EmailAddress = ?, EmailPass = ?, Alias = ?, PhoneNumber = ?, Extension = ?, FaxNumber = ?, HomeNumber = ?, CellNumber = ?, TollFree = ? WHERE ContactsID = ?";

            PreparedStatement preparedStatement = dbLogic.connect("EDIT-CONTACT").prepareStatement(statement);

            preparedStatement.setString(1, contact.getContactTitle());
            preparedStatement.setString(2, contact.getName());
            preparedStatement.setString(3, contact.getEmailAddress());
            preparedStatement.setString(4, contact.getEmailPass());
            preparedStatement.setString(5, contact.getAlias());
            preparedStatement.setString(6, contact.getPhoneNumber());
            preparedStatement.setString(7, contact.getExtension());
            preparedStatement.setString(8, contact.getFaxNumber());
            preparedStatement.setString(9, contact.getHomeNumber());
            preparedStatement.setString(10, contact.getCellNumber());
            preparedStatement.setString(11, contact.getTollFree());
            preparedStatement.setInt(12, contact.getContactID());

            System.out.println("[DB-EDIT-CONTACT] - Editing Contact.");
            preparedStatement.executeUpdate();
            System.out.println("[DB-EDIT-CONTACT]\t -- Done.");
        }
        catch (SQLException e){
            System.out.println("[DB-EDIT-CONTACT-ERR!] - " + e.getMessage());
            //e.printStackTrace();
        }
    }

    /** remove contact from contact table */
    public static void removeContact(int contactID){
        try (Connection conn = dbLogic.connect("CONTACT-DELETE");
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM " + globalVariables.CONTACT_TABLE_NAME + " WHERE ContactsID = " + contactID);

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** get all contact info from table -- uses custom model to access/modify values */
    public static ObservableList<Contact> getContactData(int CustomerID){
        ObservableList<Contact> rsData = FXCollections.observableArrayList();

        String getSize = "SELECT COUNT(*) FROM Contact WHERE CustomerID = " + CustomerID;
        String sql = "SELECT * FROM Contact WHERE CustomerID = " + CustomerID;

        try (Connection conn = dbLogic.connect("CONTACT-ALL-QUERY");
             Statement debugGetRowCount = conn.createStatement();
             Statement getDataStmt = conn.createStatement();
             ResultSet rs = getDataStmt.executeQuery(sql)){

            if(DEBUG) {
                int rowCount = debugGetRowCount.executeQuery(getSize).getInt(1);
                System.out.println("[CONTACT-ALL-QUERY] - Data Size: " + rowCount);
            }

            // loop through the result set
            while(rs.next()){
                Contact contact = new Contact();

                contact.setContactID(rs.getInt("ContactsID"));
                contact.setCustomerID(rs.getInt("CustomerID"));
                contact.setContactTitle(rs.getString("ContactTitle"));
                contact.setName(rs.getString("Name"));
                contact.setEmailAddress(rs.getString("EmailAddress"));
                contact.setEmailPass(rs.getString("EmailPass"));
                contact.setAlias(rs.getString("Alias"));
                contact.setPhoneNumber(rs.getString("PhoneNumber"));
                contact.setExtension(rs.getString("Extension"));
                contact.setFaxNumber(rs.getString("FaxNumber"));
                contact.setHomeNumber(rs.getString("HomeNumber"));
                contact.setCellNumber(rs.getString("CellNumber"));
                contact.setTollFree(rs.getString("TollFree"));

                rsData.add(contact);
            } //endloop
        }

        catch (SQLException e) {
            System.out.println("[CONTACT-ALL-QUERY-ERR!] - " + e.getMessage());
            e.printStackTrace();
        }
        return rsData;
    }
}
