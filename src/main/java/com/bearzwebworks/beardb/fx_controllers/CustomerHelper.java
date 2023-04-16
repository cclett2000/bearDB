package com.bearzwebworks.beardb.fx_controllers;

import com.bearzwebworks.beardb.db.handler.customerHandler;
import com.bearzwebworks.beardb.db.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import static com.bearzwebworks.beardb.fx_controllers.MainView.ConsoleTag;
import static com.bearzwebworks.beardb.fx_controllers.MainView.companyNamesData;

public class CustomerHelper {
    @FXML
    /** create observable list for company dataset and store name in another list for listview*/
    protected static ObservableList<Customer> setCompanyListData (){
        ObservableList<Customer> companyData;

        System.out.println(ConsoleTag + "Populating Company List...");
        companyData = customerHandler.getCustomerData();    // get data from db via customerHandler

        // add names to observable list
        for (Customer companyDatum : companyData) {
            companyNamesData.add(companyDatum.getCompanyName());
        } //endloop

        System.out.println("\t >> (" + companyNamesData.size() + ") Name List: " + companyNamesData);
        System.out.println(ConsoleTag + "Populating Company List Done.");

        return companyData;
    }

}
