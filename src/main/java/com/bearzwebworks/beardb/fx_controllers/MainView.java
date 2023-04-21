/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb.fx_controllers;

import com.bearzwebworks.beardb.Main;
import com.bearzwebworks.beardb.db.handler.customerHandler;
import com.bearzwebworks.beardb.db.model.Customer;
import com.bearzwebworks.beardb.globalVariables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.Locale;

import static com.bearzwebworks.beardb.fx_controllers.CustomerHelper.setCompanyListData;

public class MainView {
    CustomerHelper cusHelper = new CustomerHelper();
    static String ConsoleTag = "[GUI-MAIN-VIEW]";

    protected static ObservableList<String> companyNamesData = FXCollections.observableArrayList();
    protected static ObservableList<Customer> companyData = setCompanyListData();

    int selectedIndex = -1;  //store index of the selected company

    //region FXML ListViews
    @FXML protected ListView<String> companyListView;   // customer name list for GUI
    //endregion

    //region FXML TextFields
    @FXML Label version;

    /**COMPANY METADATA */
    @FXML TextField companyNameField;
    @FXML TextField companyBillingField;
    @FXML TextField companyCityField;
    @FXML TextField companyZipField;
    @FXML TextField companyStateField;
    @FXML TextField companyCountryField;
    @FXML TextArea companyCommentsField;

    /** section placeholder */

    //endregion

    public void initialize(){
        version.setText(globalVariables.VERSION);
        companyListView.setItems(companyNamesData);

        // ensures first item clicked returns the right index
        companyListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                selectedIndex = newValue.intValue();    // set index
            } //endif
        });
    }

    //region Company Logic
    @FXML
    /** LIST_CLICK - Logic for clicking on a company name in the listView */
    protected void companyItemClicked(){
        String methodTag = ConsoleTag + "[OnClick - Company/Organization]";
        // get index of the item clicked
        companyListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                selectedIndex = newValue.intValue();    // set index
            } //endif
        });
        Customer temp = companyData.get(selectedIndex);

        System.out.println(methodTag + " | Index: " + selectedIndex + " | ID: " + temp.getCustomerID() + " | Name: " + companyListView.getSelectionModel().getSelectedItem() + " | ");

        //region populate company information
        companyNameField.setText(temp.getCompanyName());
        companyBillingField.setText(temp.getBilling());
        companyCityField.setText(temp.getCity());
        companyZipField.setText(temp.getZIP());
        companyStateField.setText(temp.getState());
        companyCountryField.setText(temp.getCountry());
        companyCommentsField.setText(temp.getComments());
        //endregion


        //TODO: populate projects with appropiate id
    }

    /** BUTTON - logic for deleting company and it's data */
    public void deleteCompanyButtonListener(ActionEvent actionEvent) throws SQLException {
        String methodTag = ConsoleTag + "[OnClick - Delete Company] ";
        if(selectedIndex > -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete '" + companyNamesData.get(selectedIndex) + "'");
            alert.setContentText("This will delete all data relative to this company and cannot be undone.");

            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                System.out.println(methodTag + "Attempting to Delete" + "\n\t >> " + companyData.get(selectedIndex));

                customerHandler.removeCustomer(companyData.get(selectedIndex).getCustomerID());

                // clear data from textfields
                companyNameField.clear();
                companyBillingField.clear();
                companyCityField.clear();
                companyZipField.clear();
                companyStateField.clear();
                companyCountryField.clear();
                companyCommentsField.clear();

                // refresh data in GUI from database
                companyNamesData.clear();                       // clear name list
                companyData.setAll(setCompanyListData());       // set to updated database

                System.out.println(methodTag + "Deleted Successfully");
            } else {
                System.out.println(methodTag + "Company Deletion cancelled.");
            }
        }else{
            System.out.println(methodTag + " No Company Selected");
        }
    }

    /** BUTTON - logic for adding company */
    public void addCompanyButtonListener(ActionEvent actionEvent){
        String methodTag = ConsoleTag + "[Button - Add New Company] ";
        Customer temp = new Customer();

        Button addCompanyButton = new Button();
        addCompanyButton.setText("Add");

        // Create the add information window controls
        Label nameLabel = new Label("Company Name:");
        TextField nameTextField = new TextField();
        Label billingLabel = new Label("Billing:");
        TextField billingTextField = new TextField();
        Label cityLabel = new Label("City:");
        TextField cityTextField = new TextField();
        Label stateLabel = new Label("State:");
        TextField stateTextField = new TextField();
        Label zipLabel = new Label("Zip:");
        TextField zipTextField = new TextField();
        Label countryLabel = new Label("Country:");
        TextField countryTextField = new TextField();

        // Add the add information window controls to a layout
        GridPane addInformationLayout = new GridPane();
        addInformationLayout.setHgap(10);
        addInformationLayout.setVgap(10);
        addInformationLayout.setPadding(new Insets(10));
        addInformationLayout.addRow(0, nameLabel, nameTextField);
        addInformationLayout.addRow(1, billingLabel, billingTextField);
        addInformationLayout.addRow(2, cityLabel, cityTextField);
        addInformationLayout.addRow(3, stateLabel, stateTextField);
        addInformationLayout.addRow(4, zipLabel, zipTextField);
        addInformationLayout.addRow(5, countryLabel, countryTextField);

        addInformationLayout.addRow(6, addCompanyButton);

        // Create the add information window scene
        // TODO: find a way to ignore OS zoom/font size
        Scene addInformationScene = new Scene(addInformationLayout, 330, 300);
        addInformationScene.getStylesheets().add(String.valueOf(Main.class.getResource("styles/layout.css")));

        // Create the add information window stage
        Stage addInformationStage = new Stage();
        addInformationStage.setTitle("Add New Company");
        addInformationStage.setScene(addInformationScene);
        addInformationStage.setResizable(false);

        // Show the add information window
        addInformationStage.show();

        // button logic
        // TODO: possibly change to allow adding companies with partial information
        addCompanyButton.setOnAction(e -> {
            if(nameTextField.getText().length() > 0 && billingTextField.getText().length() > 0 && cityTextField.getText().length() > 0 && stateTextField.getText().length() > 0 && zipLabel.getText().length() > 0 && countryTextField.getText().length() > 0) {
                // data getting logic
                temp.setCompanyName(nameTextField.getText());
                temp.setBilling(billingTextField.getText());
                temp.setCity(cityTextField.getText());
                temp.setState(stateTextField.getText());
                temp.setZIP(zipTextField.getText());
                temp.setCountry(countryTextField.getText());

                customerHandler.addCustomer(temp.getCompanyName(),
                        temp.getBilling(),
                        temp.getCity(),
                        temp.getState(),
                        temp.getZIP(),temp.getCountry(),
                        "");

                companyNamesData.clear();
                companyData.setAll(setCompanyListData());
                addInformationStage.close();
            }else {
                System.out.println(methodTag + "Can't add company, some fields are empty!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Whoops!");
                alert.setHeaderText("Can't Add New Company");
                alert.setContentText("Please fill out all fields.");
                alert.showAndWait();
            }
        });
    }

    /** BUTTON - logic for editing company information */
    public void editCompanyButtonListener(ActionEvent actionEvent) {
        String methodTag = ConsoleTag + "[Button - Save Company Info] ";
        Customer temp = new Customer();

        if (selectedIndex > -1) {
            //region get company information
            temp.setCustomerID(companyData.get(selectedIndex).getCustomerID());
            temp.setCompanyName(companyNameField.getText());
            temp.setBilling(companyBillingField.getText());
            temp.setCity(companyCityField.getText());
            temp.setZIP(companyZipField.getText());
            temp.setState(companyStateField.getText());
            temp.setCountry(companyCountryField.getText());
            temp.setComments(companyCommentsField.getText());
            //endregion

            // update company in database
            customerHandler.updateCustomer(temp.getCustomerID(),
                    temp.getCompanyName(),
                    temp.getBilling(),
                    temp.getCity(),
                    temp.getState(),
                    temp.getZIP(),
                    temp.getCountry(),
                    temp.getComments());

            companyNamesData.clear();
            companyData.setAll(setCompanyListData());

        }else{
            System.out.println(methodTag + "No Company Selected, Can't Save");
        }
    }
    //endregion

    //region Project Logic
    //endregion

    //region Contact Logic
    //endregion
}
