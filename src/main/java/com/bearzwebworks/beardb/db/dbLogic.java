/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb.db;

import java.sql.*;

/** database function handler class */
public class dbLogic {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/com/bearzwebworks/beardb/db/webhosting.sqlite";

    /** connect to db */
    public static Connection connect(String debugTag) {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:src/main/resources/com/bearzwebworks/beardb/db/webHosting.db";
            // create a connection to the database
            conn = DriverManager.getConnection(DB_URL);

            System.out.println("[DB-"+ debugTag +"-CONN] - Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    /** create new database -- not needed */
    public static void createDatabase() {

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("[DB-CREATE] - The Driver is " + meta.getDriverName());
                System.out.println("[DB-CREATE] - Database URL >> " + DB_URL);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** init db tables */
    public static void initDatabase() {
        String customersStmnt =
                """
                CREATE TABLE IF NOT EXISTS Customer(
                    CustomerID INTEGER PRIMARY KEY,
                    CompanyName TEXT,
                    Billing TEXT,
                    City TEXT,
                    State TEXT,
                    ZIP TEXT,
                    Country TEXT,
                    Comments TEXT
                );
                """;

        String contactsStmnt =
                """
                CREATE TABLE IF NOT EXISTS Contact(
                    ContactsID INTEGER PRIMARY KEY,
                    CustomerID INTEGER,
                    ContactTitle TEXT,
                    Name TEXT,
                    EmailAddress TEXT,
                    Alias TEXT,
                    PhoneNumber TEXT,
                    Extension TEXT,
                    FaxNumber TEXT,
                    HomeNumber TEXT,
                    CellNumber TEXT,
                    TollFree TEXT,
                    EmailPass TEXT
                );
                """;

        // thisstatement may not work
        // date will need to follow a format -- TEXT as ISO8601 strings ("YYYY-MM-DD HH:MM:SS.SSS").
        // store money as text, if needed for computation create a converter
        // for monthly/yearly -- 0 == false, 1 == true
        String projectsStmnt =
                """
                CREATE TABLE IF NOT EXISTS Project(
                    ProjectID INTEGER PRIMARY KEY,
                    CustomerID INTEGER,
                    HostingBeginDate TEXT,
                    HostingEndDate TEXT,
                    HostingPayment TEXT,
                    WebDesignCost TEXT,
                    DomainExpiration TEXT,
                    URL TEXT,
                    WordpressAddress TEXT,
                    WordpressLogin TEXT,
                    WordpressPassword TEXT,
                    WooCommerceUser TEXT,
                    WooCommercePass TEXT,
                    WooCommerceSerial TEXT,
                    Monthly INTEGER,
                    Yearly INTEGER
                );
                """;

        // remember integer boolean logic for payment type
        String merchantStmnt =
                """
                CREATE TABLE IF NOT EXISTS Merchant(
                    MerchantID INTEGER PRIMARY KEY,
                    CustomerID INTEGER,
                    ProjectID INTEGER,
                    StoreName TEXT,
                    MerchantProvider TEXT,
                    MerchantPhone TEXT,
                    MerchantURL TEXT,
                    Acquire TEXT,
                    AcquireContact TEXT,
                    AcquirePhone TEXT,
                    MID TEXT,
                    Visa INTEGER,
                    Master INTEGER,
                    Discover INTEGER,
                    AmEx INTEGER,
                    JCB INTEGER,
                    Diners INTEGER,
                    AcquireBIN TEXT,
                    MerchantsID TEXT,
                    Vnumber TEXT,
                    TimeZone TEXT,
                    Category TEXT,
                    AgentBIN TEXT,
                    AgentChain TEXT,
                    AgentLocation TEXT,
                    MerchantLogin TEXT,
                    MerchantPass TEXT
                );
                """;

        try {
            Statement statement = connect("INIT").createStatement();

            // table create statement execs
            statement.execute(contactsStmnt);
            System.out.println("[DB-INIT] - Contact Table Initialized.");

            statement.execute(projectsStmnt);
            System.out.println("[DB-INIT] - Project Table Initialized.");

            statement.execute(merchantStmnt);
            System.out.println("[DB-INIT] - Merchant Table Initialized.");

            statement.execute(customersStmnt);
            System.out.println("[DB-INIT] - Customer Table Initialized.");

            statement.close();
        }
        catch (SQLException e){
            System.out.println("[DB-INIT-ERR!] - " + e.getMessage());
            e.printStackTrace();
        }
    }




}
