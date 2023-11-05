package com.example.englishdictionaryapp.GUI;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import com.example.englishdictionaryapp.Database.*;
public class SearchingZone  {

    double sizeX = MainScene.sizeX;
    double sizeY = MainScene.sizeY;
    Pane searchingZone = new Pane();
    public Pane make(){
        searchingZone.setLayoutX(0);
        searchingZone.setLayoutY(sizeY/8);
        searchingZone.setStyle("-fx-background-color:#181297;");
        searchingZone.setPrefSize(sizeX,sizeY/6);

        Pane centerZone = new Pane();
        centerZone.setPrefSize(sizeX/2,sizeY/18);
        ComboBox choiceBox = new ComboBox();
        choiceBox.setPrefSize(sizeX/7.5, sizeY/18);
        choiceBox.setStyle("-fx-background-color:#F2EF1F;-fx-font: 12px \"Arial\";");
        choiceBox.getItems().add("Anh - Việt");
        choiceBox.getItems().add("Việt - Anh");
        choiceBox.setPromptText("Ngôn ngữ");
        choiceBox.setOnAction((event) -> {
            int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();

            System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
            System.out.println("   ChoiceBox.getValue(): " + choiceBox.getValue());
        });

        ComboBox comboBox = new ComboBox();
        comboBox.setLayoutX(choiceBox.getLayoutX() + sizeX/7.5);
        comboBox.setLayoutY(choiceBox.getLayoutY());
        comboBox.setPrefSize(sizeX/2 - sizeX/7.5, sizeY/18);
        comboBox.setStyle("-fx-font: 12px \"Arial\";");
        Dictionary dictionary = new Dictionary();
        for(int i = 0;i < dictionary.getSizeofDictionary();i++) {
            comboBox.getItems().add(dictionary.takeWord(i));
        }
        new AutoCompleteComboBoxListener<>(comboBox);
//        comboBox.setOnAction((event) -> {
//            int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
//            Object selectedItem = comboBox.getSelectionModel().getSelectedItem();
//            Pane WordPane = new DescriptionZone().make(selectedItem);
////           MainScene.primaryStage.setScene(MainScene.sceneWord);
//            System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
//            System.out.println("   ComboBox.getValue(): " + comboBox.getValue());
//        });
        centerZone.getChildren().addAll(choiceBox,comboBox);
        double centerX = (searchingZone.getPrefWidth() - centerZone.getPrefWidth()) / 2;
        double centerY = (searchingZone.getPrefHeight() - centerZone.getPrefHeight()) / 2;
        centerZone.setLayoutX(centerX);

        centerZone.setLayoutY(centerY);
        searchingZone.getChildren().add(centerZone);
        return searchingZone;
    }
}

