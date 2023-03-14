package com.bearzwebworks.beardb.db;

import com.bearzwebworks.beardb.db.model.Customer;
import com.bearzwebworks.beardb.db.model.Merchant;

import java.sql.*;
import java.util.ArrayList;

/** database function handler class */
public class handler {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/com/bearzwebworks/beardb/db/webHosting_sqlite.db";

    /* connect to db */
    private static Connection connect(String debugTag) {
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

    /* create new database -- not needed */
    private static void createDatabase() {

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

    /* init db tables */
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

        // this print statement may not work
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

    /* add new customer to customer table */
    public static void addCustomer(String CompanyName, String Billing, String City, String State, String ZIP, String Country, String Comments) {
        try {
            String statement = "INSERT INTO Customer (CompanyName, Billing, City, State, ZIP, Country, Comments) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connect("ADD-CUSTOMER").prepareStatement(statement);

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

    /* add new merchant to merchant table */
    public static void addMerchant(String StoreName, String MerchantProvider, String MerchantURL, String MerchantPhone, String Acquire, String AcquireContact, String AcquirePhone, String MID, int Visa, int Master, int Discover, int AmEx, int JCB, int Diners, String AcquireBIN, String MerchantsID, String Vnumber, String TimeZone, String Category, String AgentBIN, String AgentChain, String AgentLocation, String MerchantLogin, String MerchantPass){
        try {
            String statement = "INSERT INTO Customer (StoreName, MerchantProvider, MerchantURL, MerchantPhone, Acquire, AcquireContact, AcquirePhone, MID, Visa, Master, Discover, AmEx, JCB, Diners, AcquireBIN, MerchantsID, Vnumber, TimeZone, Category, AgentBIN, AgentChain, AgentLocation, MerchantLogin, MerchantPass) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connect("ADD-CUSTOMER").prepareStatement(statement);

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

    /* get all customer info from table -- uses custom model to access/modify values */
    public static ArrayList<Customer> getCustomerData(){
        ArrayList<Customer> rsData = new ArrayList<>();

        String getSize = "SELECT COUNT(*) FROM Customer";
        String sql = "SELECT * FROM Customer";

        try (Connection conn = connect("CUSTOMER-ALL-QUERY");
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
                }

                for(int i = 0; i < rsData.size(); i++){
                    System.out.println("\t" + (i+1) + ") " + rsData.get(i).toString());
                }
        }

        catch (SQLException e) {
            System.out.println("[CUSTOMER-ALL-QUERY-ERR!] - " + e.getMessage());
            e.printStackTrace();
        }
        return rsData;
    }

    /* get all merchant info from table -- uses custom model */
    public static ArrayList<Merchant> getMerchantData(){
        ArrayList<Merchant> rsData = new ArrayList<>();

        String getSize = "SELECT COUNT(*) FROM Merchant";
        String sql = "SELECT * FROM Merchant";

        try (Connection conn = connect("MERCHANT-ALL-QUERY");
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
