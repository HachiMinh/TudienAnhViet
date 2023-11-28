package com.example.tudienanhviet.zones;

import com.example.tudienanhviet.UserInterface;
import com.example.tudienanhviet.ChangeScene;
import javafx.event.ActionEvent;

public class ChoosingGame {
    public void Hangman(ActionEvent event) throws Exception {
        ChangeScene scene = new ChangeScene();
        scene.Change("hangman.fxml",UserInterface.global);
    }

    public void pronunciationGame(ActionEvent event) throws Exception {
        ChangeScene scene = new ChangeScene();
        scene.Change("pronunciation.fxml",UserInterface.global);
    }

    public void ListenGame(ActionEvent event) throws Exception {
        ChangeScene scene = new ChangeScene();
        scene.Change("listen.fxml",UserInterface.global);
    }
}
