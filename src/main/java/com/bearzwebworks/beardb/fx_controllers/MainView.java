/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb.fx_controllers;

import com.bearzwebworks.beardb.db.handler.customerHandler;
import com.bearzwebworks.beardb.db.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MainView {
    //region Variables/Data Structures
    String ConsoleTag = "[GUI-MAIN-VIEW] ";
    int selectedIndex = 0;  //store index of the selected company
    ObservableList<String> companyNamesData = FXCollections.observableArrayList();
    ObservableList<Customer> companyData = companyListData();

    @FXML
    private ListView<String> companyList;   // customer name list for GUI
    //endregion

    public void initialize(){
        companyList.setItems(companyNamesData);
    }

    //region Company Interaction Logic
    @FXML
    /** create observable list for company dataset and store name in another list for listview*/
    public ObservableList<Customer> companyListData (){
        ObservableList<Customer> companyData;

        System.out.println(ConsoleTag + "Populating Company List...");
        companyData = customerHandler.getCustomerData();    // get data from db via customerHandler

        // add names to observable list
        for (Customer companyDatum : companyData) {
            companyNamesData.add(companyDatum.getCompanyName());
        } //endloop

        System.out.println("\t" + ConsoleTag + "(" + companyNamesData.size() + ") Name List: " + companyNamesData);
        System.out.println(ConsoleTag + "Populating Company List Done.");

        return companyData;
    }

    @FXML
    /** Logic for clicking on a company name in the listView */
    protected void companyItemClicked(){
        // get index of the item clicked
        companyList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                selectedIndex = newValue.intValue();    // set index
            } //endif
        });

        System.out.println(ConsoleTag + "(OnClick)" +
                "\n\tIndex: " + selectedIndex +
                "\n\tName: " + companyList.getSelectionModel().getSelectedItem());
    }
    //endregion
}
