package com.example.tudienanhviet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.example.tudienanhviet.Database.*;

public class UserInterface extends ChangeScene implements Initializable {
    @FXML
    public AnchorPane root;
    @FXML
    public HBox menu;


    public static AnchorPane global = new AnchorPane();
    public final static Dictionaries dictionary = new Dictionaries();
    public final static ArrayList<Word> NormalWord = dictionary.normalWord();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        global = root;
        try {
            Change("searchingzone.fxml",root);
        } catch (Exception e) {System.out.println("Lỗi ở UserInterface");}

        for (Node node : menu.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (ex) -> {
                    switch (node.getAccessibleText()) {
                        case "dictionary":
                            try {Change("searchingzone.fxml", root);}
                            catch (Exception e) {}
                            break;
                        case "online_dict":
                            try {Change("googletranslate.fxml", root);}
                            catch (Exception e) {}
                            break;
                        case "game":
                            try {Change("ChooseGame.fxml", root);}
                            catch (Exception e) {}
                            break;
                    }
                });
            }
        }
    }


    public void Quit(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
