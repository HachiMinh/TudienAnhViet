package com.example.englishdictionaryapp.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import com.example.englishdictionaryapp.Database.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class AutoCompleteComboBoxListener<T> implements EventHandler<InputEvent> {

    private ComboBox comboBox;
    private StringBuilder sb;
    private ObservableList<T> data;
    private boolean moveCaretToPos = false;
    private int caretPos;

    public AutoCompleteComboBoxListener(final ComboBox comboBox) {
        this.comboBox = comboBox;
        //comboBox.setVisibleRowCount(5);
        sb = new StringBuilder();
        data = comboBox.getItems();

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(new EventHandler<InputEvent>() {

            @Override
            public void handle(InputEvent t) {
                comboBox.hide();
            }
        });
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }

    //@Override
    public void handle(InputEvent inputEvent) {
        if(inputEvent instanceof KeyEvent) {
            KeyEvent event = (KeyEvent) inputEvent;
            if (event.getCode() == KeyCode.UP) {
                caretPos = -1;
                moveCaret(comboBox.getEditor().getText().length());
                return;
            } else if (event.getCode() == KeyCode.DOWN) {
                if (!comboBox.isShowing()) {
                    comboBox.show();
                }
                caretPos = -1;
                moveCaret(comboBox.getEditor().getText().length());
                return;
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                moveCaretToPos = true;
                caretPos = comboBox.getEditor().getCaretPosition();

            } else if (event.getCode() == KeyCode.DELETE) {
                moveCaretToPos = true;
                caretPos = comboBox.getEditor().getCaretPosition();
            } else if (event.getCode() == KeyCode.ENTER) {
                comboBox.hide();
                Object selectedItem = comboBox.getSelectionModel().getSelectedItem();
                Pane WordPane = new DescriptionZone().make(selectedItem);
                VBox vBox = (VBox) MainScene.sceneRoot.getRoot();
                AnchorPane anchorPane = (AnchorPane)vBox.getChildren().get(1);
                if(anchorPane.getChildren().size() > 2) {
                    anchorPane.getChildren().remove(2);
                }
                anchorPane.getChildren().add(WordPane);
                vBox.getChildren().remove(1);
                vBox.getChildren().add(anchorPane);
                MainScene.sceneRoot.setRoot(vBox);
                MainScene.primaryStage.setScene(MainScene.sceneRoot);
                return;
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                    || event.isControlDown() || event.getCode() == KeyCode.HOME
                    || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                return;
            }
        } else if (inputEvent instanceof MouseEvent){
            MouseEvent event1 = (MouseEvent) inputEvent;
            if (event1.isPrimaryButtonDown()){
                comboBox.hide();
                Object selectedItem = comboBox.getSelectionModel().getSelectedItem();
                Pane WordPane = new DescriptionZone().make(selectedItem);
                return;
            }
        }


        ObservableList list = FXCollections.observableArrayList();
        if (AutoCompleteComboBoxListener.this.comboBox
                .getEditor().getText().toLowerCase() == "") {
            list = data;
        }
         else {for (int i=0; i<data.size() & list.size() < 30; i++) {
            if(data.get(i).toString().toLowerCase().startsWith(
                    AutoCompleteComboBoxListener.this.comboBox
                            .getEditor().getText().toLowerCase())) {
                list.add(data.get(i));
            }
        } }
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        comboBox.getEditor().setText(t);
        if(!moveCaretToPos) {
            caretPos = -1;
        }
        comboBox.setVisibleRowCount(Math.min(10,list.size()));
        moveCaret(t.length());
        if(!list.isEmpty()) {
            comboBox.hide();
            comboBox.setVisibleRowCount(Math.min(10,list.size()));
            comboBox.show();
        } else {
            comboBox.hide();
        }
    }

    private void moveCaret(int textLength) {
        if(caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }

}