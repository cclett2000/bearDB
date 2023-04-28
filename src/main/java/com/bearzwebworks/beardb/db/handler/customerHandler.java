/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb.db.handler;

import com.bearzwebworks.beardb.db.dbLogic;
import com.bearzwebworks.beardb.globalVariables;
import com.bearzwebworks.beardb.db.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class customerHandler {
    static final boolean DEBUG = false;

    /** add new customer to customer table */
    public static void addCustomer(String CompanyName, String Billing, String City, String State, String ZIP, String Country, String Comments) {
        try {
            String statement = "INSERT INTO Customer (CompanyName, Billing, City, State, ZIP, Country, Comments) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = dbLogic.connect("ADD-CUSTOMER").prepareStatement(statement);

            preparedStatement.setString(1, CompanyName);
            preparedStatement.setString(2, Billing);
            preparedStatement.setString(3, City);
            preparedStatement.setString(4, State);
            preparedStatement.setString(5, ZIP);
            preparedStatement.setString(6, Country);
            preparedStatement.setString(7, Comments);

            System.out.println("[DB-ADD-CUSTOMER] - Adding New Customer.");
            preparedStatement.executeUpdate();
            System.out.println("[DB-ADD-CUSTOMER]\t -- Done.");
        }
        catch (SQLException e){
            System.out.println("[DB-ADD-CUSTOMER-ERR!] - " + e.getMessage());
            e.printStackTrace();
        }

    }

    /** update customer information in customer table */
    public static void updateCustomer(int customerID, String CompanyName, String Billing, String City, String State, String ZIP, String Country, String Comments) {
        try {
            String statement = "UPDATE " + globalVariables.CUSTOMER_TABLE_NAME + " SET CompanyName = ?, Billing = ?, City = ?, State = ?, ZIP = ?, Country = ?, Comments = ? WHERE CustomerID = ?";
            PreparedStatement preparedStatement = dbLogic.connect("EDIT-CUSTOMER").prepareStatement(statement);

            preparedStatement.setString(1, CompanyName);
            preparedStatement.setString(2, Billing);
            preparedStatement.setString(3, City);
            preparedStatement.setString(4, State);
            preparedStatement.setString(5, ZIP);
            preparedStatement.setString(6, Country);
            preparedStatement.setString(7, Comments);
            preparedStatement.setInt(8, customerID);

            System.out.println("[DB-EDIT-CUSTOMER] - Editing Customer.");
            preparedStatement.executeUpdate();
            System.out.println("[DB-EDIT-CUSTOMER]\t -- Done.");
        }
        catch (SQLException e){
            System.out.println("[DB-EDIT-CUSTOMER-ERR!] - " + e.getMessage());
            //e.printStackTrace();
        }

    }

    /** remove entity (customer) from database */
    //TODO: test other table functionality
    public static void removeCustomer(int itemId){
        try (Connection conn = dbLogic.connect("CUSTOMER-DELETE");
             Statement stmt = conn.createStatement()) {

            // Start transaction
            conn.setAutoCommit(false);

            // Execute DELETE statements for each table
            stmt.executeUpdate("DELETE FROM " + globalVariables.CUSTOMER_TABLE_NAME + " WHERE CustomerID = " + itemId);
            stmt.executeUpdate("DELETE FROM " + globalVariables.CONTACT_TABLE_NAME + " WHERE CustomerID = " + itemId);
            stmt.executeUpdate("DELETE FROM " + globalVariables.MERCHANT_TABLE_NAME + " WHERE CustomerID = " + itemId);
            stmt.executeUpdate("DELETE FROM " + globalVariables.PROJECT_TABLE_NAME + " WHERE CustomerID = " + itemId);

            // Commit the transaction
            conn.commit();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** get all customer info from table -- uses custom model to access/modify values */
    public static ObservableList<Customer> getCustomerData(){
        ObservableList<Customer> rsData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Customer";

        try (Connection conn = dbLogic.connect("CUSTOMER-ALL-QUERY");
             Statement stmt = conn.createStatement();
             Statement getRowCount = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            if(DEBUG) {
                String getSize = "SELECT COUNT(*) FROM Customer";
                int rowCount = getRowCount.executeQuery(getSize).getInt(1);
                System.out.println("[CUSTOMER-ALL-QUERY] - Data Size: " + rowCount);
            }

            // loop through the result set
            while(rs.next()){
                    Customer customer = new Customer();

                    customer.setCustomerID(rs.getInt("CustomerID"));
                    customer.setCompanyName(rs.getString("CompanyName"));
                    customer.setBilling(rs.getString("Billing"));
                    customer.setCity(rs.getString("City"));
                    customer.setState(rs.getString("State"));
                    customer.setZIP(rs.getString("ZIP"));
                    customer.setCountry(rs.getString("Country"));
                    customer.setComments(rs.getString("Comments"));

                    rsData.add(customer);
                } //endloop
            }

        catch (SQLException e) {
            System.out.println("[CUSTOMER-ALL-QUERY-ERR!] - " + e.getMessage());
            e.printStackTrace();
        }
        return rsData;
    }
}
