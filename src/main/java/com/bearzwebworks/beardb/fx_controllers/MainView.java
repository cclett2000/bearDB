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
import com.bearzwebworks.beardb.db.handler.projectHandler;
import com.bearzwebworks.beardb.db.model.Contact;
import com.bearzwebworks.beardb.db.model.Customer;
import com.bearzwebworks.beardb.db.model.Project;
import com.bearzwebworks.beardb.globalVariables;
import com.bearzwebworks.beardb.util.copyrightHandler;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

public class MainView {
    final boolean DEBUG_MODE = true;
    static String ConsoleTag = "[GUI-MAIN-VIEW]";

    // CUSTOMER
    protected static ObservableList<String> companyNamesData = FXCollections.observableArrayList();
    protected static ObservableList<Customer> companyData = setCompanyListData();

    // CONTACT
    protected static ObservableList<String> contactNameData = FXCollections.observableArrayList();
    protected static ObservableList<Contact> contactData = FXCollections.observableArrayList();

    // PROJECT
    protected static ObservableList<String> projectNameData = FXCollections.observableArrayList();
    protected static ObservableList<Project> projectData = FXCollections.observableArrayList();

    protected int companySelectedIndex = -1;  // store index of the selected company
    protected int contactSelectedIndex = -1;  // store index of selected contact
    protected int projectSelectedIndex = -1;  // store index of selected project

    //region FXML ListViews
    @FXML protected ListView<String> companyListView = new ListView<>();   // customer name list for GUI
    @FXML protected ListView<String> contactListView = new ListView<>();   // contact name list for GUI
    @FXML protected ListView<String> projectListView = new ListView<>();   // project name list for GUI
    //endregion

    //region Filtered Lists -- Search Feature
    protected FilteredList<String> companyNameFilteredList = new FilteredList<>(companyNamesData);
    protected FilteredList<Customer> companyDataFilteredList = new FilteredList<>(companyData);

    protected FilteredList<String> contactNameFilteredList = new FilteredList<>(contactNameData);
    protected FilteredList<Contact> contactDataFilteredList = new FilteredList<>(contactData);

    protected FilteredList<String> projectNameFilteredList = new FilteredList<>(projectNameData);
    protected FilteredList<Project> projectDataFilteredList = new FilteredList<>(projectData);
    //endRegion

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

    /** PROJECT METADATA */
    //radio button
    @FXML private RadioButton payTypeMonthlyRadBtn;
    @FXML private RadioButton payTypeYearlyRadBtn;

    @FXML private DatePicker hostStartDateField;
    @FXML private DatePicker hostEndDateField;
    @FXML private DatePicker domainExpirationField;

    @FXML private TextField costField;
    @FXML private TextField designCostField;

    @FXML private TextField urlField;
    @FXML private TextField wooCommerceUsernameField;
    @FXML private TextField wooCommercePasswordField;
    @FXML private TextField wooCommerceSerialField;
    @FXML private TextField wordpressAddressField;
    @FXML private TextField wordpressLoginField;
    @FXML private TextField wordpressPasswordField;

    /** SEARCH BAR STUFF */
    @FXML private TextField companySearchBar;
    @FXML private TextField projectSearchBar;
    @FXML private TextField contactSearchBar;

    /** ABOUT STUFF */
    @FXML private Text copyrightField;
    //endregion

    public void initialize(){
        version.setText(globalVariables.VERSION);
        copyrightField.setText(copyrightHandler.getCopyright());

        // init radio button listener
        payTypeMonthlyRadBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // if radioButton1 is selected, clear the selection of radioButton2
                payTypeYearlyRadBtn.setSelected(false);
            }
        });
        payTypeYearlyRadBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // if radioButton1 is selected, clear the selection of radioButton2
                payTypeMonthlyRadBtn.setSelected(false);
            }
        });

        // will hopefully only allow numeric values for the textfields
        costField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                costField.setText(oldValue);
            }
        });
        designCostField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                designCostField.setText(oldValue);
            }
        });

        // init name data
        companyListView.setItems(companyNamesData);
        contactListView.setItems(contactNameData);
        projectListView.setItems(projectNameData);

        //search init
        companySearchInit();
        projectSearchInit();
        contactSearchInit();

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
        projectListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                projectSelectedIndex = newValue.intValue();    // set index
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
        Customer temp = companyDataFilteredList.get(companySelectedIndex);

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
        contactNameData.clear();                                        // purge contact data to ensure list is updated and not duplicated
        contactData.setAll(setContactListData(temp.getCustomerID()));

        /** POPULATE PROJECT LIST LOGIC */
        projectNameData.clear();                                        // purge project data to ensure list is updated and not duplicated
        projectData.setAll(setProjectListData(temp.getCustomerID()));

    }

    /** BUTTON - logic for deleting company and it's data */
    public void deleteCompanyButtonListener(ActionEvent actionEvent) throws SQLException {
        String methodTag = ConsoleTag + "[OnClick - Delete Company] ";
        if(companySelectedIndex > -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete '" + companyNameFilteredList.get(companySelectedIndex) + "'");
            alert.setContentText("This will delete all data relative to this company and cannot be undone.");

            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                System.out.println(methodTag + "Attempting to Delete" + "\n\t >> " + companyDataFilteredList.get(companySelectedIndex));

                customerHandler.removeCustomer(companyDataFilteredList.get(companySelectedIndex).getCustomerID());

                clearCompanySearchButtonListener(actionEvent);
                clearAllFields("all");

                // refresh data in GUI from database
                contactNameData.clear();
                projectNameData.clear();
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

        // Add the add information window controls to a layout
        GridPane addInformationLayout = new GridPane();
        addInformationLayout.setHgap(10);
        addInformationLayout.setVgap(10);
        addInformationLayout.setPadding(new Insets(10));
        addInformationLayout.addRow(0, nameLabel, nameTextField);

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
            if (nameTextField.getText().length() == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Whoops!");
                alert.setHeaderText("Can't Add New Company");
                alert.setContentText("Please fill out the 'Company Name' field.");
                alert.showAndWait();
            }
            else if (companyNamesData.contains(nameTextField.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Whoops!");
                alert.setHeaderText("Can't Add New Company");
                alert.setContentText("Company Already Exists!");
                alert.showAndWait();
            }
            else {
                // data getting logic
                temp.setCompanyName(nameTextField.getText());

                customerHandler.addCustomer(temp.getCompanyName(),
                        temp.getBilling(),
                        temp.getCity(),
                        temp.getState(),
                        temp.getZIP(),temp.getCountry(),
                        "");

                companyNamesData.clear();
                companyData.setAll(setCompanyListData());

                clearCompanySearchButtonListener(actionEvent);
                clearAllFields("all");

                addInformationStage.close();
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
            companySelectedIndex = -1;

            clearCompanySearchButtonListener(actionEvent);
            clearAllFields("all");

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
    /** LIST_CLICK - Logic for clicking on a project name in the listView */
    public void projectItemClicked(){
        String methodTag = ConsoleTag + "[OnClick - Project]";

        // get index of the item clicked
        projectListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                projectSelectedIndex = newValue.intValue();    // set index
            } //endif
        });

        Project temp = projectDataFilteredList.get(projectSelectedIndex);

        System.out.println(methodTag + " Index: " + projectSelectedIndex + " | ID: " + temp.getProjectID() + " | Name: " + projectListView.getSelectionModel().getSelectedItem() + " | ");

        //region populate company information

        // set radio button according to db pay type data
        if(temp.getIsMonthly() == 1){
            payTypeYearlyRadBtn.setSelected(false);
            payTypeMonthlyRadBtn.setSelected(true);
        }
        else if(temp.getIsYearly() == 1){
            payTypeMonthlyRadBtn.setSelected(false);
            payTypeYearlyRadBtn.setSelected(true);
        }
        else{
            payTypeMonthlyRadBtn.setSelected(false);
            payTypeYearlyRadBtn.setSelected(false);
        }

        // parse and show dates from db
        if(temp.getHostingBeginDate() != null )
            hostStartDateField.setValue(LocalDate.parse(temp.getHostingBeginDate()));
        else
            hostStartDateField.setValue(null);

        if(temp.getHostingEndDate() != null)
            hostEndDateField.setValue(LocalDate.parse(temp.getHostingEndDate()));
        else
            hostEndDateField.setValue(null);

        if(temp.getDomainExpiration() != null)
            domainExpirationField.setValue(LocalDate.parse(temp.getDomainExpiration()));
        else
            domainExpirationField.setValue(null);


        urlField.setText(temp.getURL());
        costField.setText(String.valueOf(temp.getHostingPayment()));                    // GUI is Cost, DB is HostingPayment -- no idea why the OG db/gui has such a disparity but it's too much of a hassle tp change the recreated db now XD
        designCostField.setText(String.valueOf(temp.getWebDesignCost()));
        wooCommerceUsernameField.setText(temp.getWooCommerceUser());
        wooCommercePasswordField.setText(temp.getWooCommercePass());
        wooCommerceSerialField.setText(temp.getWooCommerceSerial());
        wordpressAddressField.setText(temp.getWordpressAddress());
        wordpressLoginField.setText(temp.getWordpressLogin());
        wordpressPasswordField.setText(temp.getWordpressPassword());
        //endregion
    }

    /** BUTTON - logic for adding project */
    public void addProjectButtonListener(ActionEvent actionEvent){
        String methodTag = ConsoleTag + "[Button - Add New Project] ";

        if(companySelectedIndex < 0){
            System.out.println(methodTag + "Can't add project, No company selected!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Add New Project");
            alert.setHeaderText("No Company Selected!");
            alert.setContentText("Make sure you've selected a company in the 'Company/Organization' panel");
            alert.showAndWait();
        }
        else {
            Project temp = new Project();

            Button addProjectButton = new Button();
            addProjectButton.setText("Add");

            // Create the add information window controls
            Label UrlLabel = new Label("Project URL:");
            TextField UrlField = new TextField();

            // Add the add information window controls to a layout
            GridPane addInformationLayout = new GridPane();
            addInformationLayout.setHgap(10);
            addInformationLayout.setVgap(10);
            addInformationLayout.setPadding(new Insets(10));
            addInformationLayout.addRow(0, UrlLabel, UrlField);

            addInformationLayout.addRow(1, addProjectButton);

            // Create the add information window scene
            Scene addInformationScene = new Scene(addInformationLayout, 330, 100);
            addInformationScene.getStylesheets().add(String.valueOf(Main.class.getResource("styles/layout.css")));

            // Create the add information window stage
            Stage addInformationStage = new Stage();
            addInformationStage.setTitle("Add New Project");
            addInformationStage.setScene(addInformationScene);
            addInformationStage.setResizable(false);

            // Show the add information window
            addInformationStage.show();

            // button logic
            addProjectButton.setOnAction(e -> {
                if (projectNameData.contains(UrlField.getText())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Whoops!");
                    alert.setHeaderText("Can't Add New Project");
                    alert.setContentText("Project Already Exists!");
                    alert.showAndWait();
                }
                else if (UrlField.getText().isEmpty()){
                    System.out.println(methodTag + "Can't add project, 'Project URL' field is empty!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Whoops!");
                    alert.setHeaderText("Can't Add New Project");
                    alert.setContentText("Please fill out the 'Project URL' field.");
                    alert.showAndWait();
                }
                else {
                    // data getting logic
                    temp.setCustomerID(companyDataFilteredList.get(companySelectedIndex).getCustomerID());
                    temp.setURL(UrlField.getText());

                    projectHandler.addProject(temp);

                    projectNameData.clear();
                    projectData.setAll(setProjectListData(companyDataFilteredList.get(companySelectedIndex).getCustomerID()));

                    clearProjectSearchButtonListener(actionEvent);
                    clearAllFields("project");

                    addInformationStage.close();
                }
            });
        }
    }

    /** BUTTON - logic for editing project */
    public void editProjectButtonListener(ActionEvent actionEvent){
        String methodTag = ConsoleTag + "[Button - Save Project Info] ";
        Project temp = new Project();

        if (projectSelectedIndex > -1) {
            //region get contact information
            if(payTypeMonthlyRadBtn.isSelected()) {
                temp.setIsMonthly(1);
                temp.setIsYearly(0);
            }

            if(payTypeYearlyRadBtn.isSelected()){
                temp.setIsYearly(1);
                temp.setIsMonthly(0);
            }

            // date null check
            if (hostStartDateField.getValue() != null)
                temp.setHostingBeginDate(hostStartDateField.getValue().toString());
            if(hostEndDateField.getValue() != null)
                temp.setHostingEndDate(hostEndDateField.getValue().toString());
            if(domainExpirationField.getValue() != null)
                temp.setDomainExpiration(domainExpirationField.getValue().toString());

            temp.setProjectID(projectDataFilteredList.get(projectSelectedIndex).getProjectID());
            temp.setCustomerID(projectDataFilteredList.get(projectSelectedIndex).getCustomerID());
            temp.setHostingPayment(Double.parseDouble(costField.getText()));
            temp.setWebDesignCost(Double.parseDouble(designCostField.getText()));
            temp.setURL(urlField.getText());
            temp.setWooCommerceUser(wooCommerceUsernameField.getText());
            temp.setWooCommercePass(wooCommercePasswordField.getText());
            temp.setWooCommerceSerial(wooCommerceSerialField.getText());
            temp.setWordpressAddress(wordpressAddressField.getText());
            temp.setWordpressLogin(wordpressLoginField.getText());
            temp.setWordpressPassword(wordpressPasswordField.getText());

            if(DEBUG_MODE)
                System.out.println(methodTag + "Project Data Temp Var: " + temp.toString());
            //endregion

            // update contact in database
            projectHandler.updateProject(temp);

            projectNameData.clear();
            projectData.setAll(setProjectListData(companyDataFilteredList.get(companySelectedIndex).getCustomerID()));

            clearAllFields("project");
            clearProjectSearchButtonListener(actionEvent);

            projectSelectedIndex = -1;

        }else{
            System.out.println(methodTag + "No Project Selected, Can't Save");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Save Project Changes");
            alert.setHeaderText("No Project Selected!");
            alert.setContentText("Make sure you've selected a company in the 'Company/Organization' panel");
            alert.showAndWait();
        }
    }

    /** BUTTON - logic for removing project */
    public void deleteProjectButtonListener(ActionEvent actionEvent){
        String methodTag = ConsoleTag + "[OnClick - Delete Project] ";
        if(companySelectedIndex > -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete '" + projectNameFilteredList.get(projectSelectedIndex) + "'");
            alert.setContentText("This will delete the project and cannot be undone.");

            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                if (DEBUG_MODE)
                    System.out.println(methodTag + "Attempting to Delete" + "\n\t >> " + projectDataFilteredList.get(projectSelectedIndex));

                projectHandler.removeProject(projectDataFilteredList.get(projectSelectedIndex).getProjectID());

                // refresh data in GUI from database
                projectNameData.clear();     // clear name list
                projectData.setAll(setProjectListData(companyDataFilteredList.get(companySelectedIndex).getCustomerID()));       // set to updated database

                clearAllFields("project");
                clearProjectSearchButtonListener(actionEvent);

                System.out.println(methodTag + "Deleted Successfully");
            }
            else {
                System.out.println(methodTag + "Project Deletion cancelled.");
            }
        }else{
            System.out.println(methodTag + " No Project Selected");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Delete Project");
            alert.setHeaderText("No Project Selected!");
            alert.setContentText("Make sure you've selected a company in the 'Company/Organization' panel");
            alert.showAndWait();
        }
    }
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

        Contact temp = contactDataFilteredList.get(contactSelectedIndex);

        System.out.println(methodTag + " Index: " + contactSelectedIndex + " | ID: " + temp.getContactID() + " | Name: " + contactListView.getSelectionModel().getSelectedItem() + " | ");

        //region populate contact information
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
        String methodTag = ConsoleTag + "[Button - Add New Contact] ";

        if(companySelectedIndex < 0){
            System.out.println(methodTag + "Can't add contact, No company selected!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Add New Contact");
            alert.setHeaderText("No Company Selected!");
            alert.setContentText("Make sure you've selected a company in the 'Company/Organization' panel");
            alert.showAndWait();
        }
        else {
            Contact temp = new Contact();

            Button addCompanyButton = new Button();
            addCompanyButton.setText("Add");

            // Create the add information window controls
            Label nameLabel = new Label("Contact Name:");
            TextField nameTextField = new TextField();

            // Add the add information window controls to a layout
            GridPane addInformationLayout = new GridPane();
            addInformationLayout.setHgap(10);
            addInformationLayout.setVgap(10);
            addInformationLayout.setPadding(new Insets(10));
            addInformationLayout.addRow(0, nameLabel, nameTextField);

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
            addCompanyButton.setOnAction(e -> {
                if (contactNameData.contains(nameTextField.getText())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Whoops!");
                    alert.setHeaderText("Can't Add New Contact");
                    alert.setContentText("Contact Already Exists!");
                    alert.showAndWait();
                }
                else if (nameTextField.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Whoops!");
                    alert.setHeaderText("Can't Add New Contact");
                    alert.setContentText("Please fill out the 'Contact Name' field.");
                    alert.showAndWait();
                }
                else {
                    // data getting logic
                    temp.setCustomerID(companyDataFilteredList.get(companySelectedIndex).getCustomerID());
                    temp.setName(nameTextField.getText());

                    contactHandler.addContact(temp);

                    contactNameData.clear();
                    contactData.setAll(setContactListData(companyDataFilteredList.get(companySelectedIndex).getCustomerID()));

                    clearAllFields("contact");
                    clearContactSearchButtonListener(actionEvent);

                    addInformationStage.close();
                }
            });
        }
    }

    /** BUTTON - logic for editing contact */
    public void editContactButtonListener(ActionEvent actionEvent){
        String methodTag = ConsoleTag + "[Button - Save Contact Info] ";
        Contact temp = new Contact();

        if (contactSelectedIndex > -1) {
            //region get contact information
            temp.setContactID(contactDataFilteredList.get(contactSelectedIndex).getContactID());
            temp.setCustomerID(contactDataFilteredList.get(contactSelectedIndex).getCustomerID());
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
            contactData.setAll(setContactListData(companyDataFilteredList.get(companySelectedIndex).getCustomerID()));

            clearContactSearchButtonListener(actionEvent);
            clearAllFields("contact");

            contactSelectedIndex = -1;

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
            alert.setHeaderText("Are you sure you want to delete '" + contactNameFilteredList.get(contactSelectedIndex) + "'");
            alert.setContentText("This will delete the contact and cannot be undone.");

            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                System.out.println(methodTag + "Attempting to Delete" + "\n\t >> " + contactDataFilteredList.get(contactSelectedIndex));

                contactHandler.removeContact(contactDataFilteredList.get(contactSelectedIndex).getContactID());

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
                contactData.setAll(setContactListData(companyDataFilteredList.get(companySelectedIndex).getCustomerID()));       // set to updated database

                clearContactSearchButtonListener(actionEvent);
                clearAllFields("contact");

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

    //region UTIL
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
        projectData = projectHandler.getProjectData(customerID);    // get data from db via projectHandler

        // add names to observable list
        for (Project projectDatum : projectData) {
            projectNameData.add(projectDatum.getURL());
        } //endloop

        System.out.println("\t >> (" + projectNameData.size() + ") Project List: " + projectNameData);
        System.out.println(ConsoleTag + " Populating Project List Done.");

        return projectData;
    }

    /** clear company search bar logic */
    public void clearCompanySearchButtonListener(ActionEvent actionEvent){
        companySearchBar.clear();
    }
    /** clear project search bar logic */
    public void clearProjectSearchButtonListener(ActionEvent actionEvent){
        projectSearchBar.clear();
    }
    /** clear contact search bar logic */
    public void clearContactSearchButtonListener(ActionEvent actionEvent){
        contactSearchBar.clear();
    }

    /** clear textfields; arg selection = 'all', 'company', 'contact', 'project' */
    private void clearAllFields(String clearType){
        //COMPANY
        if (clearType.equals("all") || clearType.equals("company")) {
            // clear data from textfields
            companyNameField.clear();
            companyBillingField.clear();
            companyCityField.clear();
            companyZipField.clear();
            companyStateField.clear();
            companyCountryField.clear();
            companyCommentsField.clear();
        }

        // CONTACT
        if (clearType.equals("all") || clearType.equals("contact")) {
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
        }

        // PROJECT
        if (clearType.equals("all") || clearType.equals("project")) {
            payTypeMonthlyRadBtn.setSelected(false);
            payTypeYearlyRadBtn.setSelected(false);

            hostStartDateField.setValue(null);
            hostEndDateField.setValue(null);
            domainExpirationField.setValue(null);

            costField.clear();
            designCostField.clear();

            urlField.clear();
            wooCommerceUsernameField.clear();
            wooCommercePasswordField.clear();
            wooCommerceSerialField.clear();
            wordpressAddressField.clear();
            wordpressLoginField.clear();
            wordpressPasswordField.clear();
        }
    }
    //endregion

    //region SEARCH
    /** company search bar logic method */
    protected void companySearchInit(){
        companySearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            String companySearchText = newValue.toLowerCase().trim();
            if (companySearchText.isEmpty()) {
                companyNameFilteredList.setPredicate(null);
                companyDataFilteredList.setPredicate(null);
            }
            else{
                // Otherwise, show only items that contain the search text
                companyNameFilteredList.setPredicate(item -> item.toLowerCase().contains(companySearchText));

                // modified to sync with name list
                companyDataFilteredList.setPredicate(item -> item.getCompanyName().toLowerCase().contains(companySearchText));
            }
            companyListView.setItems(companyNameFilteredList);
        });
        // Add a ListChangeListener to update the filtered list whenever the original list changes
        companyNamesData.addListener((ListChangeListener<String>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    companyNameFilteredList.setPredicate(null); // Clear the predicate to ensure all items are shown

                    // reset data to show all
                    companyListView.setItems(companyNameFilteredList);
                }
            }
        });
    }

    /** company search bar logic method */
    protected void projectSearchInit(){
        projectSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            String projectSearchText = newValue.toLowerCase().trim();
            if (projectSearchText.isEmpty()) {
                projectNameFilteredList.setPredicate(null);
                projectDataFilteredList.setPredicate(null);
            }
            else{
                // Otherwise, show only items that contain the search text
                projectNameFilteredList.setPredicate(item -> item.toLowerCase().contains(projectSearchText));

                // modified to sync with name list
                projectDataFilteredList.setPredicate(item -> item.getURL().toLowerCase().contains(projectSearchText));
            }
            projectListView.setItems(projectNameFilteredList);
        });
        // Add a ListChangeListener to update the filtered list whenever the original list changes
        projectNameData.addListener((ListChangeListener<String>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    projectNameFilteredList.setPredicate(null); // Clear the predicate to ensure all items are shown

                    // reset data to show all
                    projectListView.setItems(projectNameFilteredList);
                }
            }
        });
    }

    /** company search bar logic method */
    protected void contactSearchInit(){
        contactSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            String contactSearchText = newValue.toLowerCase().trim();
            if (contactSearchText.isEmpty()) {
                contactNameFilteredList.setPredicate(null);
                contactDataFilteredList.setPredicate(null);
            }
            else{
                // Otherwise, show only items that contain the search text
                contactNameFilteredList.setPredicate(item -> item.toLowerCase().contains(contactSearchText));

                // modified to sync with name list
                contactDataFilteredList.setPredicate(item -> item.getName().toLowerCase().contains(contactSearchText));
            }
            contactListView.setItems(contactNameFilteredList);
        });
        // Add a ListChangeListener to update the filtered list whenever the original list changes
        contactNameData.addListener((ListChangeListener<String>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    contactNameFilteredList.setPredicate(null); // Clear the predicate to ensure all items are shown

                    // reset data to show all
                    contactListView.setItems(contactNameFilteredList);
                }
            }
        });
    }
    //endregion
}
