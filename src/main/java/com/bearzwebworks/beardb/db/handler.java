package com.bearzwebworks.beardb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** database function handler class */
public class handler {

    /* connect to db */
    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/sqlite/db/webhosting.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("[DB-CONN] - Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
