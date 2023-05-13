/**
 * Copyright (c) 2023 Charles Lett Jr. All rights reserved.
 *
 * This code is the property of Charles Lett Jr. and may not be used or distributed without permission.
 * Unauthorized use or distribution of this code may result in legal action.
*/

package com.bearzwebworks.beardb;

import com.bearzwebworks.beardb.db.dbLogic;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        //db init
        dbLogic.createDatabase();
        dbLogic.initDatabase();

        System.setProperty("glass.win.uiScale", "100%");

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        URL cssURL = getClass().getResource("styles/layout.css");
        if (cssURL != null) {
            String cssPath = cssURL.toExternalForm();
            scene.getStylesheets().add(cssPath);
        }
        else{
            throw new NullPointerException("CSS File Not Found");
        }

        stage.setTitle("Company Information Manager");
        stage.getIcons().add(new Image(String.valueOf(Main.class.getResource("img/logo.png"))));
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}