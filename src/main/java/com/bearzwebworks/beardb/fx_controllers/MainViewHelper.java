/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb.fx_controllers;

import com.bearzwebworks.beardb.db.handler.contactHandler;
import com.bearzwebworks.beardb.db.handler.customerHandler;
import com.bearzwebworks.beardb.db.model.Contact;
import com.bearzwebworks.beardb.db.model.Customer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import static com.bearzwebworks.beardb.fx_controllers.MainView.*;

public class MainViewHelper {
    @FXML
    /** create observable list for company dataset and store name in another list for listview*/
    protected static ObservableList<Customer> setCompanyListData (){
        ObservableList<Customer> companyData;

        System.out.println(ConsoleTag + " Populating Company List...");
        companyData = customerHandler.getCustomerData();    // get data from db via customerHandler

        // add names to observable list
        for (Customer companyDatum : companyData) {
            companyNamesData.add(companyDatum.getCompanyName());
        } //endloop

        System.out.println("\t >> (" + companyNamesData.size() + ") Name List: " + companyNamesData);
        System.out.println(ConsoleTag + " Populating Company List Done.");

        return companyData;
    }

    protected static ObservableList<Contact> setContactListData(int customerID){
        ObservableList<Contact> contactData;

        System.out.println(ConsoleTag + " Populating Contact List...");
        contactData = contactHandler.getContactData(customerID);    // get data from db via customerHandler

        // add names to observable list
        for (Contact contactDatum : contactData) {
            contactNameData.add(contactDatum.getName());
        } //endloop

        System.out.println("\t >> (" + contactNameData.size() + ") Name List: " + contactNameData);
        System.out.println(ConsoleTag + " Populating Contact List Done.");

        return contactData;
    }

}
