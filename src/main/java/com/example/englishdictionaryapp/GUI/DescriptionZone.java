package com.example.englishdictionaryapp.GUI;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import com.example.englishdictionaryapp.Database.*;

import java.util.InputMismatchException;

public class DescriptionZone {
    double sizeX = MainScene.sizeX;
    double sizeY = MainScene.sizeY;

    public Pane make(Object word){
        Pane descriptionZone = new Pane();
        TextArea textArea = new TextArea();
        descriptionZone.setLayoutX(sizeX/10);
        descriptionZone.setLayoutY(sizeY*7/24);
        descriptionZone.setPrefSize(sizeX-sizeX*2/10,sizeY*17/24);
        if (word instanceof String) {
            Dictionary d = new Dictionary();
            for(int i = 0; i < d.getSizeofDictionary(); i++) {
                if(word.equals(d.takeWord(i).getWord_spelling())) {
                    Word w = d.takeWord(i);
                    String ans = w.print();
                    textArea.setText(ans);
                }
            }
            textArea.setStyle("-fx-background-color:white;");
            textArea.setPrefSize(sizeX-sizeX*2/10,sizeY*17/24);
            descriptionZone.getChildren().add(textArea);
            ScrollBar scrollBar = new ScrollBar();
        } else {
            throw new InputMismatchException("Có lỗi xảy ra");
        }
        return descriptionZone;
    }
}
