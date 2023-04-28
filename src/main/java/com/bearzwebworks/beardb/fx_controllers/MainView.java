/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb.fx_controllers;

import com.bearzwebworks.beardb.Main;
import com.bearzwebworks.beardb.db.handler.contactHandler;
import com.bearzwebworks.beardb.db.handler.customerHandler;
import com.bearzwebworks.beardb.db.model.Contact;
import com.bearzwebworks.beardb.db.model.Customer;
import com.bearzwebworks.beardb.globalVariables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.bearzwebworks.beardb.fx_controllers.MainViewHelper.setCompanyListData;
import static com.bearzwebworks.beardb.fx_controllers.MainViewHelper.setContactListData;

public class MainView {
    static String ConsoleTag = "[GUI-MAIN-VIEW]";

    // CUSTOMER
    protected static ObservableList<String> companyNamesData = FXCollections.observableArrayList();
    protected static ObservableList<Customer> companyData = setCompanyListData();

    // CONTACT
    protected static ObservableList<String> contactNameData = FXCollections.observableArrayList();
    protected static ObservableList<Contact> contactData = FXCollections.observableArrayList();

    protected int companySelectedIndex = -1;  // store index of the selected company
    protected int contactSelectedIndex = -1;  // store index of selected contact

    //region FXML ListViews
    @FXML protected ListView<String> companyListView;   // customer name list for GUI
    @FXML protected ListView<String> contactListView;   // contact name list for GUI
    //endregion

    //region FXML TextFields
    @FXML private Label version;

    /** COMPANY METADATA */
    @FXML private TextField companyNameField;
    @FXML private TextField companyBillingField;
    @FXML private TextField companyCityField;
    @FXML private TextField companyZipField;
    @FXML private TextField companyStateField;
    @FXML private TextField companyCountryField;
    @FXML private TextArea companyCommentsField;

    /** CONTACT METADATA */
    @FXML private TextField contactNameField;
    @FXML private TextField contactTitleField;
    @FXML private TextField contactEmailField;
    @FXML private TextField contactEmailPassField;
    @FXML private TextField contactAliasField;
    @FXML private TextField contactExtensionField;
    @FXML private TextField contactFaxNumField;
    @FXML private TextField contactHomeNumField;
    @FXML private TextField contactCellNumField;
    @FXML private TextField contactTollFreeNumField;

    /** section placeholder */

    //endregion

    public void initialize(){
        version.setText(globalVariables.VERSION);
        companyListView.setItems(companyNamesData);
        contactListView.setItems(contactNameData);

        // ensures first item clicked returns the right index
        companyListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                companySelectedIndex = newValue.intValue();    // set index
            } //endif
        });
        contactListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                contactSelectedIndex = newValue.intValue();    // set index
            } //endif
        });
    }

    //region Company Logic
    @FXML
    /** LIST_CLICK - Logic for clicking on a company name in the listView */
    public void companyItemClicked(){
        String methodTag = ConsoleTag + "[OnClick - Company/Organization]";

        // get index of the item clicked
        companyListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                companySelectedIndex = newValue.intValue();    // set index
            } //endif
        });
        Customer temp = companyData.get(companySelectedIndex);

        System.out.println(methodTag + " Index: " + companySelectedIndex + " | ID: " + temp.getCustomerID() + " | Name: " + companyListView.getSelectionModel().getSelectedItem() + " | ");

        //region populate company information
        companyNameField.setText(temp.getCompanyName());
        companyBillingField.setText(temp.getBilling());
        companyCityField.setText(temp.getCity());
        companyZipField.setText(temp.getZIP());
        companyStateField.setText(temp.getState());
        companyCountryField.setText(temp.getCountry());
        companyCommentsField.setText(temp.getComments());
        //endregion


        /** POPULATE CONTACT LIST LOGIC */
        // purge contact data to ensure list is updated and not duplicated
        contactNameData.clear();
        contactData.setAll(setContactListData(temp.getCustomerID()));
    }

    /** BUTTON - logic for deleting company and it's data */
    public void deleteCompanyButtonListener(ActionEvent actionEvent) throws SQLException {
        String methodTag = ConsoleTag + "[OnClick - Delete Company] ";
        if(companySelectedIndex > -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete '" + companyNamesData.get(companySelectedIndex) + "'");
            alert.setContentText("This will delete all data relative to this company and cannot be undone.");

            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                System.out.println(methodTag + "Attempting to Delete" + "\n\t >> " + companyData.get(companySelectedIndex));

                customerHandler.removeCustomer(companyData.get(companySelectedIndex).getCustomerID());

                // clear data from textfields
                companyNameField.clear();
                companyBillingField.clear();
                companyCityField.clear();
                companyZipField.clear();
                companyStateField.clear();
                companyCountryField.clear();
                companyCommentsField.clear();

                contactNameField.clear();
                contactTitleField.clear();
                contactEmailField.clear();
                contactEmailPassField.clear();
                contactAliasField.clear();
                contactExtensionField.clear();
                contactFaxNumField.clear();
                contactHomeNumField.clear();
                contactCellNumField.clear();
                contactTollFreeNumField.clear();

                // refresh data in GUI from database
                contactNameData.clear();
                companyNamesData.clear();                       // clear name list
                companyData.setAll(setCompanyListData());       // set to updated database

                System.out.println(methodTag + "Deleted Successfully");
            } else {
                System.out.println(methodTag + "Company Deletion cancelled.");
            }
        }else{
            System.out.println(methodTag + " No Company Selected");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Delete Company");
            alert.setHeaderText("No Company Selected!");
            alert.setContentText("Make sure you've selected a company in the 'Company/Organization' panel");
            alert.showAndWait();
        }
    }

    /** BUTTON - logic for adding company */
    public void addCompanyButtonListener(ActionEvent actionEvent){
        String methodTag = ConsoleTag + "[Button - Add New Company] ";
        Customer temp = new Customer();

        Button addCompanyButton = new Button("Add");

        // Create the add information window controls
        Label nameLabel = new Label("Company Name:");
        TextField nameTextField = new TextField();
//        Label billingLabel = new Label("Billing:");
//        TextField billingTextField = new TextField();
//        Label cityLabel = new Label("City:");
//        TextField cityTextField = new TextField();
//        Label stateLabel = new Label("State:");
//        TextField stateTextField = new TextField();
//        Label zipLabel = new Label("Zip:");
//        TextField zipTextField = new TextField();
//        Label countryLabel = new Label("Country:");
//        TextField countryTextField = new TextField();

        // Add the add information window controls to a layout
        GridPane addInformationLayout = new GridPane();
        addInformationLayout.setHgap(10);
        addInformationLayout.setVgap(10);
        addInformationLayout.setPadding(new Insets(10));
        addInformationLayout.addRow(0, nameLabel, nameTextField);
//        addInformationLayout.addRow(1, billingLabel, billingTextField);
//        addInformationLayout.addRow(2, cityLabel, cityTextField);
//        addInformationLayout.addRow(3, stateLabel, stateTextField);
//        addInformationLayout.addRow(4, zipLabel, zipTextField);
//        addInformationLayout.addRow(5, countryLabel, countryTextField);

        addInformationLayout.addRow(1, addCompanyButton);

        // Create the add information window scene
        // TODO: find a way to ignore OS zoom/font size
        Scene addInformationScene = new Scene(addInformationLayout, 330, 100);
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
            // if(nameTextField.getText().length() > 0 && billingTextField.getText().length() > 0 && cityTextField.getText().length() > 0 && stateTextField.getText().length() > 0 && zipLabel.getText().length() > 0 && countryTextField.getText().length() > 0)
            if(nameTextField.getText().length()  > 0) {
                // data getting logic
                temp.setCompanyName(nameTextField.getText());
//                temp.setBilling(billingTextField.getText());
//                temp.setCity(cityTextField.getText());
//                temp.setState(stateTextField.getText());
//                temp.setZIP(zipTextField.getText());
//                temp.setCountry(countryTextField.getText());

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
                System.out.println(methodTag + "Can't add company, 'Company Name' field is empty!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Whoops!");
                alert.setHeaderText("Can't Add New Company");
                alert.setContentText("Please fill out the 'Company Name' field.");
                alert.showAndWait();
            }
        });
    }

    /** BUTTON - logic for editing company information */
    public void editCompanyButtonListener(ActionEvent actionEvent) {
        String methodTag = ConsoleTag + "[Button - Save Company Info] ";
        Customer temp = new Customer();

        if (companySelectedIndex > -1) {
            //region get company information
            temp.setCustomerID(companyData.get(companySelectedIndex).getCustomerID());
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Save Company Changes");
            alert.setHeaderText("No Company Selected!");
            alert.setContentText("Make sure you've selected a company in the 'Company/Organization' panel");
            alert.showAndWait();
        }
    }
    //endregion

    //region Project Logic
    //endregion

    //region Contact Logic
    /** LIST_CLICK - Logic for clicking on a contact name in the listView */
    public void contactItemClicked(){
        String methodTag = ConsoleTag + "[OnClick - Contact]";

        // get index of the item clicked
        contactListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                contactSelectedIndex = newValue.intValue();    // set index
            } //endif
        });

        Contact temp = contactData.get(contactSelectedIndex);

        System.out.println(methodTag + " Index: " + contactSelectedIndex + " | ID: " + temp.getContactID() + " | Name: " + contactListView.getSelectionModel().getSelectedItem() + " | ");

        //region populate company information
        contactNameField.setText(temp.getName());
        contactTitleField.setText(temp.getContactTitle());
        contactEmailField.setText(temp.getEmailAddress());
        contactEmailPassField.setText(temp.getEmailPass());
        contactAliasField.setText(temp.getAlias());
        contactExtensionField.setText(temp.getExtension());
        contactFaxNumField.setText(temp.getFaxNumber());
        contactHomeNumField.setText(temp.getHomeNumber());
        contactCellNumField.setText(temp.getCellNumber());
        contactTollFreeNumField.setText(temp.getTollFree());
        //endregion
    }

    /** BUTTON - logic for adding contact */
    public void addContactButtonListener(ActionEvent actionEvent){
        String methodTag = ConsoleTag + "[Button - Add New Company] ";
        Contact temp = new Contact();

        Button addCompanyButton = new Button();
        addCompanyButton.setText("Add");

        // Create the add information window controls
        Label nameLabel = new Label("Contact Name:");
        TextField nameTextField = new TextField();
//        Label emailLabel = new Label("Email:");
//        TextField emailTextField = new TextField();

        // Add the add information window controls to a layout
        GridPane addInformationLayout = new GridPane();
        addInformationLayout.setHgap(10);
        addInformationLayout.setVgap(10);
        addInformationLayout.setPadding(new Insets(10));
        addInformationLayout.addRow(0, nameLabel, nameTextField);
//        addInformationLayout.addRow(1, emailLabel, emailTextField);

        addInformationLayout.addRow(1, addCompanyButton);

        // Create the add information window scene
        // TODO: find a way to ignore OS zoom/font size
        Scene addInformationScene = new Scene(addInformationLayout, 330, 100);
        addInformationScene.getStylesheets().add(String.valueOf(Main.class.getResource("styles/layout.css")));

        // Create the add information window stage
        Stage addInformationStage = new Stage();
        addInformationStage.setTitle("Add New Contact");
        addInformationStage.setScene(addInformationScene);
        addInformationStage.setResizable(false);

        // Show the add information window
        addInformationStage.show();

        // button logic
        // TODO: possibly change to allow adding companies with partial information
        addCompanyButton.setOnAction(e -> {
//            if(nameTextField.getText().length() > 0 && emailTextField.getText().length() > 0)
            if(nameTextField.getText().length() > 0) {
                // data getting logic
                temp.setCustomerID(companyData.get(companySelectedIndex).getCustomerID());
                temp.setName(nameTextField.getText());
//                temp.setEmailAddress(emailTextField.getText());

                contactHandler.addContact(temp);

                contactNameData.clear();
                contactData.setAll(setContactListData(companyData.get(companySelectedIndex).getCustomerID()));
                addInformationStage.close();
            }else {
                System.out.println(methodTag + "Can't add contact, 'Contact Name' field are empty!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Whoops!");
                alert.setHeaderText("Can't Add New Contact");
                alert.setContentText("Please fill out the 'Contact Name' field.");
                alert.showAndWait();
            }
        });
    }

    /** BUTTON - logic for editing contact */
    public void editContactButtonListener(ActionEvent actionEvent){
        String methodTag = ConsoleTag + "[Button - Save Contact Info] ";
        Contact temp = new Contact();

        if (contactSelectedIndex > -1) {
            //region get contact information
            temp.setContactID(contactData.get(contactSelectedIndex).getContactID());
            temp.setCustomerID(contactData.get(contactSelectedIndex).getCustomerID());
            temp.setName(contactNameField.getText());
            temp.setContactTitle(contactTitleField.getText());
            temp.setEmailAddress(contactEmailField.getText());
            temp.setEmailPass(contactEmailPassField.getText());
            temp.setAlias(contactAliasField.getText());
            temp.setExtension(contactExtensionField.getText());
            temp.setFaxNumber(contactFaxNumField.getText());
            temp.setHomeNumber(contactHomeNumField.getText());
            temp.setCellNumber(contactCellNumField.getText());
            temp.setTollFree(contactTollFreeNumField.getText());
            System.out.println(methodTag + "Contact Data Temp Var: " + temp.toString());
            //endregion

            // update contact in database
            contactHandler.updateContact(temp);

            contactNameData.clear();
            contactData.setAll(setContactListData(companyData.get(companySelectedIndex).getCustomerID()));

        }else{
            System.out.println(methodTag + "No Contact Selected, Can't Save");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Save Contact Changes");
            alert.setHeaderText("No Contact Selected!");
            alert.setContentText("Make sure you've selected a company in the 'Company/Organization' panel");
            alert.showAndWait();
        }
    }

    /** BUTTON - logic for removing contact */
    public void deleteContactButtonListener(ActionEvent actionEvent){
        String methodTag = ConsoleTag + "[OnClick - Delete Contact] ";
        if(companySelectedIndex > -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete '" + contactNameData.get(contactSelectedIndex) + "'");
            alert.setContentText("This will delete the contact and cannot be undone.");

            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                System.out.println(methodTag + "Attempting to Delete" + "\n\t >> " + contactData.get(contactSelectedIndex));

                contactHandler.removeContact(contactData.get(contactSelectedIndex).getContactID());

                // clear data from textfields
                contactNameField.clear();
                contactTitleField.clear();
                contactEmailField.clear();
                contactEmailPassField.clear();
                contactAliasField.clear();
                contactExtensionField.clear();
                contactFaxNumField.clear();
                contactHomeNumField.clear();
                contactCellNumField.clear();
                contactTollFreeNumField.clear();

                // refresh data in GUI from database
                contactNameData.clear();     // clear name list
                contactData.setAll(setContactListData(companyData.get(companySelectedIndex).getCustomerID()));       // set to updated database

                System.out.println(methodTag + "Deleted Successfully");
            } else {
                System.out.println(methodTag + "Contact Deletion cancelled.");
            }
        }else{
            System.out.println(methodTag + " No Contact Selected");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Delete Contact");
            alert.setHeaderText("No Contact Selected!");
            alert.setContentText("Make sure you've selected a company in the 'Company/Organization' panel");
            alert.showAndWait();
        }
    }
    //endregion
}
