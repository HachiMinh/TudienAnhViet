package com.example.englishdictionaryapp.GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import com.example.englishdictionaryapp.Database.*;

public class ApplicationName {
    double sizeX = MainScene.sizeX;
    double sizeY = MainScene.sizeY;

    StackPane pane = new StackPane();
    public StackPane make() throws Exception {

        Font font = Font.font("Times New Roman", FontWeight.EXTRA_BOLD, sizeY/16);
        Text title = new Text("Từ điển Anh - Việt");
        title.setFill(Color.web("3C1B57"));
        title.setFont(font);
        pane.setPrefSize(sizeX/2.4,sizeY/8);
        pane.getChildren().add(title);
        pane.setAlignment(title, Pos.CENTER);
        //pane.setBackground(Background.fill(Color.web("white")));
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        return pane;
    }
}
