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

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class contactHandler {
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
}
