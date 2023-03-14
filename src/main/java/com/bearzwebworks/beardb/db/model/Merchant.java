package com.bearzwebworks.beardb.db.model;

public class Merchant {
    private int CustomerID;
    private int ProjectID;
    private String StoreName;
    private String MerchantProvider;
    private String MerchantPhone;
    private String MerchantURL;
    private String Acquire;
    private String AcquireContact;
    private String AcquirePhone;
    private String MID;
    private int Visa;
    private int Master;
    private int Discover;
    private int AmEx;
    private int JCB;
    private int Diners;
    private String AcquireBIN;
    private String MerchantsID;
    private String Vnumber;
    private String TimeZone;
    private String Category;
    private String AgentBIN;
    private String AgentChain;
    private String AgentLocation;

    @Override
    public String toString() {
        return  "CustomerID=" + CustomerID +
                ", ProjectID=" + ProjectID +
                ", StoreName='" + StoreName + '\'' +
                ", MerchantProvider='" + MerchantProvider + '\'' +
                ", MerchantPhone='" + MerchantPhone + '\'' +
                ", MerchantURL='" + MerchantURL + '\'' +
                ", Acquire='" + Acquire + '\'' +
                ", AcquireContact='" + AcquireContact + '\'' +
                ", AcquirePhone='" + AcquirePhone + '\'' +
                ", MID='" + MID + '\'' +
                ", Visa=" + Visa +
                ", Master=" + Master +
                ", Discover=" + Discover +
                ", AmEx=" + AmEx +
                ", JCB=" + JCB +
                ", Diners=" + Diners +
                ", AcquireBIN='" + AcquireBIN + '\'' +
                ", MerchantsID='" + MerchantsID + '\'' +
                ", Vnumber='" + Vnumber + '\'' +
                ", TimeZone='" + TimeZone + '\'' +
                ", Category='" + Category + '\'' +
                ", AgentBIN='" + AgentBIN + '\'' +
                ", AgentChain='" + AgentChain + '\'' +
                ", AgentLocation='" + AgentLocation + '\'' +
                ", MerchantLogin='" + MerchantLogin + '\'' +
                ", MerchantPass='" + MerchantPass + '\'' +
                '}';
    }

    private String MerchantLogin;

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public int getProjectID() {
        return ProjectID;
    }

    public void setProjectID(int projectID) {
        ProjectID = projectID;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getMerchantProvider() {
        return MerchantProvider;
    }

    public void setMerchantProvider(String merchantProvider) {
        MerchantProvider = merchantProvider;
    }

    public String getMerchantPhone() {
        return MerchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        MerchantPhone = merchantPhone;
    }

    public String getMerchantURL() {
        return MerchantURL;
    }

    public void setMerchantURL(String merchantURL) {
        MerchantURL = merchantURL;
    }

    public String getAcquire() {
        return Acquire;
    }

    public void setAcquire(String acquire) {
        Acquire = acquire;
    }

    public String getAcquireContact() {
        return AcquireContact;
    }

    public void setAcquireContact(String acquireContact) {
        AcquireContact = acquireContact;
    }

    public String getAcquirePhone() {
        return AcquirePhone;
    }

    public void setAcquirePhone(String acquirePhone) {
        AcquirePhone = acquirePhone;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public int getVisa() {
        return Visa;
    }

    public void setVisa(int visa) {
        Visa = visa;
    }

    public int getMaster() {
        return Master;
    }

    public void setMaster(int master) {
        Master = master;
    }

    public int getDiscover() {
        return Discover;
    }

    public void setDiscover(int discover) {
        Discover = discover;
    }

    public int getAmEx() {
        return AmEx;
    }

    public void setAmEx(int amEx) {
        AmEx = amEx;
    }

    public int getJCB() {
        return JCB;
    }

    public void setJCB(int JCB) {
        this.JCB = JCB;
    }

    public int getDiners() {
        return Diners;
    }

    public void setDiners(int diners) {
        Diners = diners;
    }

    public String getAcquireBIN() {
        return AcquireBIN;
    }

    public void setAcquireBIN(String acquireBIN) {
        AcquireBIN = acquireBIN;
    }

    public String getMerchantsID() {
        return MerchantsID;
    }

    public void setMerchantsID(String merchantsID) {
        MerchantsID = merchantsID;
    }

    public String getVnumber() {
        return Vnumber;
    }

    public void setVnumber(String vnumber) {
        Vnumber = vnumber;
    }

    public String getTimeZone() {
        return TimeZone;
    }

    public void setTimeZone(String timeZone) {
        TimeZone = timeZone;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getAgentBIN() {
        return AgentBIN;
    }

    public void setAgentBIN(String agentBIN) {
        AgentBIN = agentBIN;
    }

    public String getAgentChain() {
        return AgentChain;
    }

    public void setAgentChain(String agentChain) {
        AgentChain = agentChain;
    }

    public String getAgentLocation() {
        return AgentLocation;
    }

    public void setAgentLocation(String agentLocation) {
        AgentLocation = agentLocation;
    }

    public String getMerchantLogin() {
        return MerchantLogin;
    }

    public void setMerchantLogin(String merchantLogin) {
        MerchantLogin = merchantLogin;
    }

    public String getMerchantPass() {
        return MerchantPass;
    }

    public void setMerchantPass(String merchantPass) {
        MerchantPass = merchantPass;
    }

    private String MerchantPass;
}
