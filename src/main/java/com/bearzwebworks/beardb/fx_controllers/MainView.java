package com.bearzwebworks.beardb.fx_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class MainView {
    ObservableList<String> compTest = FXCollections.observableArrayList();

    @FXML
    private ListView<String> companyList;
    @FXML
    private Button populateCompList;

    // runs automatically when calling this view
    public void initialize(){
        populateCompList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                populateCompListAction();
                companyList.setItems(FXCollections.observableList(compTest));
            }
        });

        //companyList.setEditable(true);

    }

    @FXML
    // button function method
    protected void populateCompListAction(){
        System.out.println("Button Press Received");
        for(int i = 0; i<25; i++){
            System.out.println("Reached Test Loop! " + i);
            compTest.add("Sample Company " + i);
        }
        System.out.println(compTest.toString());
    }

    @FXML
    protected void listItemClicked(){
        System.out.println("Company Item Clicked!");
    }
}
