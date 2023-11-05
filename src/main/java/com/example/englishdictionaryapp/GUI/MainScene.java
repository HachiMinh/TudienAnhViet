package com.example.englishdictionaryapp.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.layout.Background;
import com.example.englishdictionaryapp.Database.*;

public class MainScene extends Application {
    public static final double sizeX = 720;
    public static final double sizeY = 420;

    public static Scene sceneRoot;
    public static Scene sceneWord;

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        MenuBar menuBar = new MenuPane().make();
        StackPane titlePane = new ApplicationName().make(); // Logo Từ điển Tiếng Anh;
        AnchorPane anchorPane = new AnchorPane();
        Pane SearchingZone = new SearchingZone().make();
        anchorPane.getChildren().addAll(titlePane, SearchingZone);
        VBox rootBox = new VBox();
        rootBox.getChildren().addAll(menuBar,anchorPane);
        rootBox.setBackground(new Background(new BackgroundFill( new LinearGradient(
                0, 0, 1, 1, true,                      //sizing
                CycleMethod.NO_CYCLE,                  //cycling
                new Stop(0, Color.web("#81c483")),     //colors
                new Stop(1, Color.web("#fcc200")))
        , CornerRadii.EMPTY, Insets.EMPTY)));
        sceneRoot = new Scene(rootBox, sizeX, sizeY);
        primaryStage.setScene(sceneRoot);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Từ điển Anh - Việt");
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
