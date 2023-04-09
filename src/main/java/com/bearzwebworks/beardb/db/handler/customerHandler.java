/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb.db.handler;

import com.bearzwebworks.beardb.db.dbLogic;
import com.bearzwebworks.beardb.db.model.Customer;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class customerHandler {
    /** add new customer to customer table */
    public static void addCustomer(String CompanyName, String Billing, String City, String State, String ZIP, String Country, String Comments) {
        try {
            String statement = "INSERT INTO Customer (CompanyName, Billing, City, State, ZIP, Country, Comments) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = dbLogic.connect("ADD-CUSTOMER").prepareStatement(statement);

            // testing
//        preparedStatement.setString(1, "Billie Jeans");
//        preparedStatement.setString(2, "Billing INF");
//        preparedStatement.setString(3, "Tulsa");
//        preparedStatement.setString(4, "OK");
//        preparedStatement.setString(5, "73050");
//        preparedStatement.setString(6, "US");
//        preparedStatement.setString(7, "BRUH FUCK DIS");

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

    /*TODO: Remove customer method
    *  - remove by ID?*/

    /** get all customer info from table -- uses custom model to access/modify values */
    public static ObservableList<Customer> getCustomerData(){
        ObservableList<Customer> rsData = FXCollections.observableArrayList();

        String getSize = "SELECT COUNT(*) FROM Customer";
        String sql = "SELECT * FROM Customer";

        try (Connection conn = dbLogic.connect("CUSTOMER-ALL-QUERY");
             Statement stmt = conn.createStatement();
             Statement getRowCount = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
                int rowCount = getRowCount.executeQuery(getSize).getInt(1);

                // loop through the result set
                System.out.println("[CUSTOMER-ALL-QUERY] - Data Size: " + rowCount);
                while(rs.next()){
                    Customer customer = new Customer();

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
