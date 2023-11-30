package com.example.tudienanhviet.zones;

import com.example.tudienanhviet.API;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class GoogleAPI implements Initializable {

    @FXML
    private TextArea main_text;
    @FXML
    private TextArea trans_text;
    @FXML
    private Label main_label;
    @FXML
    private Label trans_label;

    private String language1 = "English";
    private String language2 = "Vietnamese";

    private API api;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        main_label.setText(language1);
        trans_label.setText(language2);
        main_text.setOnKeyReleased(new EventHandler<InputEvent>() {
            @Override
            public void handle(InputEvent t) {
                if (t instanceof KeyEvent) {
                    //KeyEvent event = (KeyEvent) t;
                    try {
                        findans();
                    } catch (Exception e) {}
                }
            }
        });
    }

    public void swap(ActionEvent event) throws Exception {
        String tmp = language1;
        language1 = language2;
        language2 = tmp;
        main_label.setText(language1);
        trans_label.setText(language2);
    }

    public void findans() throws Exception {
        if (main_text.getText().trim().isEmpty()) return;
        String s = main_text.getText();
        api = new API(s, language1, language2);
        api.valueProperty().addListener((observable, oldValue, newValue) -> trans_text.setText(String.valueOf(newValue)));
        System.out.println(s);
        Thread th = new Thread(api);
        th.setDaemon(true);
        th.start();
    }
}
