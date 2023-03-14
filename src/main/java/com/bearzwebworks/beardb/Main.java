package com.bearzwebworks.beardb;

import com.bearzwebworks.beardb.db.handler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //scene.getStylesheets().add(String.valueOf(Main.class.getResource("styles/layout.css")));
        stage.setTitle("Bearz Company DB Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();
        handler.initDatabase();
        handler.addCustomer("CompName", "Address", "Tulsa", "OK", "55203", "US", "Comments");
        handler.getCustomerData();
        handler.getMerchantData();
    }
}