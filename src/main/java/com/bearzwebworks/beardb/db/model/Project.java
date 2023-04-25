/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb.db.model;

public class Project {
    private String ProjectName;
    private int CustomerID;
    private String HostingBeginDate;    //need date formatter
    private String HostingEndDate;      //need date formatter
    private double HostingPayment;      //format for money?
    private double WebDesignCost;       //format for money?
    private String DomainExpiration;    //need date formatter?
    private String URL;
    private String WordpressAddress;
    private String WordpressLogin;
    private String WordpressPassword;
    private String WooCommerceUser;
    private String WooCommercePass;
    private String WooCommerceSerial;
    private int isMonthly;              //boolean
    private int isYearly;               //boolean

    @Override
    public String toString() {
        return "Project{" +
                "CustomerID=" + CustomerID +
                ", HostingBeginDate='" + HostingBeginDate + '\'' +
                ", HostingEndDate='" + HostingEndDate + '\'' +
                ", HostingPayment=" + HostingPayment +
                ", WebDesignCost=" + WebDesignCost +
                ", DomainExpiration='" + DomainExpiration + '\'' +
                ", URL='" + URL + '\'' +
                ", WordpressAddress='" + WordpressAddress + '\'' +
                ", WordpressLogin='" + WordpressLogin + '\'' +
                ", WordpressPassword='" + WordpressPassword + '\'' +
                ", WooCommerceUser='" + WooCommerceUser + '\'' +
                ", WooCommercePass='" + WooCommercePass + '\'' +
                ", WooCommerceSerial='" + WooCommerceSerial + '\'' +
                ", isMonthly=" + isMonthly +
                ", isYearly=" + isYearly +
                '}';
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getHostingBeginDate() {
        return HostingBeginDate;
    }

    public void setHostingBeginDate(String hostingBeginDate) {
        HostingBeginDate = hostingBeginDate;
    }

    public String getHostingEndDate() {
        return HostingEndDate;
    }

    public void setHostingEndDate(String hostingEndDate) {
        HostingEndDate = hostingEndDate;
    }

    public double getHostingPayment() {
        return HostingPayment;
    }

    public void setHostingPayment(double hostingPayment) {
        HostingPayment = hostingPayment;
    }

    public double getWebDesignCost() {
        return WebDesignCost;
    }

    public void setWebDesignCost(double webDesignCost) {
        WebDesignCost = webDesignCost;
    }

    public String getDomainExpiration() {
        return DomainExpiration;
    }

    public void setDomainExpiration(String domainExpiration) {
        DomainExpiration = domainExpiration;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getWordpressAddress() {
        return WordpressAddress;
    }

    public void setWordpressAddress(String wordpressAddress) {
        WordpressAddress = wordpressAddress;
    }

    public String getWordpressLogin() {
        return WordpressLogin;
    }

    public void setWordpressLogin(String wordpressLogin) {
        WordpressLogin = wordpressLogin;
    }

    public String getWordpressPassword() {
        return WordpressPassword;
    }

    public void setWordpressPassword(String wordpressPassword) {
        WordpressPassword = wordpressPassword;
    }

    public String getWooCommerceUser() {
        return WooCommerceUser;
    }

    public void setWooCommerceUser(String wooCommerceUser) {
        WooCommerceUser = wooCommerceUser;
    }

    public String getWooCommercePass() {
        return WooCommercePass;
    }

    public void setWooCommercePass(String wooCommercePass) {
        WooCommercePass = wooCommercePass;
    }

    public String getWooCommerceSerial() {
        return WooCommerceSerial;
    }

    public void setWooCommerceSerial(String wooCommerceSerial) {
        WooCommerceSerial = wooCommerceSerial;
    }

    public int getIsMonthly() {
        return isMonthly;
    }

    public void setIsMonthly(int isMonthly) {
        this.isMonthly = isMonthly;
    }

    public int getIsYearly() {
        return isYearly;
    }

    public void setIsYearly(int isYearly) {
        this.isYearly = isYearly;
    }
}
