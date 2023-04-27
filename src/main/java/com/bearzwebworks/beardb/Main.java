/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb;

import com.bearzwebworks.beardb.db.dbLogic;
import com.bearzwebworks.beardb.db.handler.contactHandler;
import com.bearzwebworks.beardb.db.model.Contact;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        //db init
        dbLogic.createDatabase();
        dbLogic.initDatabase();

        System.setProperty("glass.win.uiScale", "100%");

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        scene.getStylesheets().add(String.valueOf(Main.class.getResource("styles/layout.css")));

        stage.setTitle("Bearz Company DB Manager");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        Contact contact = new Contact();

//        contact.setCustomerID(1);
//        contact.setContactTitle("Title");
//        contact.setName("Name");
//        contact.setEmailAddress("EmailAddy");
//        contact.setEmailPass("EmailPassy");
//        contact.setAlias("Alias");
//        contact.setPhoneNumber("PhoneNumber");
//        contact.setExtension("Extension");
//        contact.setFaxNumber("FaxNumber");
//        contact.setHomeNumber("HomeNumber");
//        contact.setCellNumber("CellNumber");
//        contact.setTollFree("TollFree");
//
//        contactHandler.addContact(contact);
//
//        System.exit(419);

        launch();
    }
}