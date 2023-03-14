package com.bearzwebworks.beardb.db.model;

public class Customer {
    private String CompanyName;
    private String Billing;
    private String City;
    private String State;
    private String ZIP;
    private String Country;
    private String Comments;

    @Override
    public String toString() {
        return  "CompanyName='" + CompanyName + '\'' +
                ", Billing='" + Billing + '\'' +
                ", City='" + City + '\'' +
                ", State='" + State + '\'' +
                ", ZIP='" + ZIP + '\'' +
                ", Country='" + Country + '\'' +
                ", Comments='" + Comments + '\'' +
                '}';
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getBilling() {
        return Billing;
    }

    public void setBilling(String billing) {
        Billing = billing;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }
}
