/**
 * Copyright 2026 Charles Lett Jr.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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