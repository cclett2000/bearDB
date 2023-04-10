/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb.db.handler;

import com.bearzwebworks.beardb.db.dbLogic;
import com.bearzwebworks.beardb.db.model.Merchant;

import java.sql.*;
import java.util.ArrayList;

public class merchantHandler {
    /** add new merchant to merchant table */
    public static void addMerchant(String StoreName, String MerchantProvider, String MerchantURL, String MerchantPhone, String Acquire, String AcquireContact, String AcquirePhone, String MID, int Visa, int Master, int Discover, int AmEx, int JCB, int Diners, String AcquireBIN, String MerchantsID, String Vnumber, String TimeZone, String Category, String AgentBIN, String AgentChain, String AgentLocation, String MerchantLogin, String MerchantPass){
        try {
            String statement = "INSERT INTO Customer (StoreName, MerchantProvider, MerchantURL, MerchantPhone, Acquire, AcquireContact, AcquirePhone, MID, Visa, Master, Discover, AmEx, JCB, Diners, AcquireBIN, MerchantsID, Vnumber, TimeZone, Category, AgentBIN, AgentChain, AgentLocation, MerchantLogin, MerchantPass) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = dbLogic.connect("ADD-CUSTOMER").prepareStatement(statement);

            preparedStatement.setString(1, StoreName);
            preparedStatement.setString(2, MerchantProvider);
            preparedStatement.setString(3, MerchantPhone);
            preparedStatement.setString(4, MerchantURL);
            preparedStatement.setString(5, Acquire);
            preparedStatement.setString(6, AcquireContact);
            preparedStatement.setString(7, AcquirePhone);
            preparedStatement.setString(8, MID);
            preparedStatement.setInt(9, Visa);
            preparedStatement.setInt(10, Master);
            preparedStatement.setInt(11, Discover);
            preparedStatement.setInt(12, AmEx);
            preparedStatement.setInt(13, JCB);
            preparedStatement.setInt(14, Diners);
            preparedStatement.setString(15, AcquireBIN);
            preparedStatement.setString(16, MerchantsID);
            preparedStatement.setString(17, Vnumber);
            preparedStatement.setString(18, TimeZone);
            preparedStatement.setString(19, Category);
            preparedStatement.setString(20, AgentBIN);
            preparedStatement.setString(21, AgentChain);
            preparedStatement.setString(22, AgentLocation);
            preparedStatement.setString(23, MerchantLogin);
            preparedStatement.setString(24, MerchantPass);

            System.out.println("[DB-ADD-MERCHANT] - Adding New Merchant.");
            preparedStatement.executeUpdate();
            System.out.println("[DB-ADD-MERCHANT]\t -- Done.");
        }
        catch (SQLException e){
            System.out.println("[DB-ADD-MERCHANT-ERR!] - " + e.getMessage());
            e.printStackTrace();
        }

    }

    /** get all merchant info from table -- uses custom model */
    public static ArrayList<Merchant> getMerchantData(){
        ArrayList<Merchant> rsData = new ArrayList<>();

        String getSize = "SELECT COUNT(*) FROM Merchant";
        String sql = "SELECT * FROM Merchant";

        try (Connection conn = dbLogic.connect("MERCHANT-ALL-QUERY");
             Statement stmt = conn.createStatement();
             Statement getRowCount = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
                int rowCount = getRowCount.executeQuery(getSize).getInt(1);

                // loop through the result set
                System.out.println("[MERCHANT-ALL-QUERY] - Data Size: " + rowCount);
                while(rs.next()){
                    Merchant merchant = new Merchant();

                    merchant.setStoreName(rs.getString("StoreName"));
                    merchant.setMerchantProvider(rs.getString("MerchantProvider"));
                    merchant.setMerchantPhone(rs.getString("MerchantPhone"));
                    merchant.setMerchantURL(rs.getString("MerchantURL"));
                    merchant.setAcquire(rs.getString("Acquire"));
                    merchant.setAcquireContact(rs.getString("AcquireContact"));
                    merchant.setAcquirePhone(rs.getString("AcquirePhone"));
                    merchant.setMID(rs.getString("MID"));

                    merchant.setVisa(rs.getInt("Visa"));
                    merchant.setMaster(rs.getInt("Master"));
                    merchant.setDiscover(rs.getInt("Discover"));
                    merchant.setAmEx(rs.getInt("AmEX"));
                    merchant.setJCB(rs.getInt("JCB"));
                    merchant.setDiners(rs.getInt("Diners"));

                    merchant.setAcquireBIN(rs.getString("AcquireBIN"));
                    merchant.setMerchantsID(rs.getString("MerchantsID"));
                    merchant.setVnumber(rs.getString("Vnumber"));
                    merchant.setTimeZone(rs.getString("TimeZone"));
                    merchant.setCategory(rs.getString("Category"));
                    merchant.setAgentBIN(rs.getString("AgentBIN"));
                    merchant.setAgentChain(rs.getString("AgentChain"));
                    merchant.setAgentLocation(rs.getString("AgentLocation"));
                    merchant.setMerchantLogin(rs.getString("MerchantLogin"));
                    merchant.setMerchantPass(rs.getString("MerchantPass"));

                    rsData.add(merchant);
                }

                for(int i = 0; i < rsData.size(); i++){
                    System.out.println("\t" + (i+1) + ") " + rsData.get(i).toString());
                }
        }

        catch (SQLException e) {
            System.out.println("[MERCHANT-ALL-QUERY-ERR!] - " + e.getMessage());
            e.printStackTrace();
        }
        return rsData;
    }
}
