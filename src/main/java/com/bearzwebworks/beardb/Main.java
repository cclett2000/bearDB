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
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        //db init
        dbLogic.createDatabase();
        dbLogic.initDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        scene.getStylesheets().add(String.valueOf(Main.class.getResource("styles/layout.css")));
        stage.setTitle("Bearz Company DB Manager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}