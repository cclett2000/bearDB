/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb.fx_controllers;

import com.bearzwebworks.beardb.db.handler.contactHandler;
import com.bearzwebworks.beardb.db.handler.customerHandler;
import com.bearzwebworks.beardb.db.handler.projectHandler;
import com.bearzwebworks.beardb.db.model.Contact;
import com.bearzwebworks.beardb.db.model.Customer;
import com.bearzwebworks.beardb.db.model.Project;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import static com.bearzwebworks.beardb.fx_controllers.MainView.*;

public class MainViewHelper {
    /** create observable list for company dataset and store name in another list for listview */
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

    /** create observable list for contact dataset and store name in another list for listview */
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

    /** create observable list for project dataset and store name in another list for listview */
    protected static ObservableList<Project> setProjectListData(int customerID){
        ObservableList<Project> projectData;

        System.out.println(ConsoleTag + " Populating Project List...");
        projectData = projectHandler.getProjectData(customerID);    // get data from db via customerHandler

        // add names to observable list
        for (Project projectDatum : projectData) {
            projectNameData.add(projectDatum.getProjectName());
        } //endloop

        System.out.println("\t >> (" + contactNameData.size() + ") Name List: " + contactNameData);
        System.out.println(ConsoleTag + " Populating Contact List Done.");

        return projectData;
    }

}
