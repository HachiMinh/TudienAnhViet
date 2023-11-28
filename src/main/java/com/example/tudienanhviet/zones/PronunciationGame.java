package com.example.tudienanhviet.zones;

import com.example.tudienanhviet.ChangeScene;
import com.example.tudienanhviet.Database.Word;
import com.example.tudienanhviet.UserInterface;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Objects;

public class PronunciationGame implements Initializable {

    @FXML
    private ImageView life5;
    @FXML
    private ImageView life4;
    @FXML
    private ImageView life3;
    @FXML
    private ImageView life2;
    @FXML
    private ImageView life1;
    @FXML
    private ImageView PronImage;
    @FXML
    private JFXTextField answer_text;
    @FXML
    private JFXTextField pronunciation_text;
    @FXML
    private JFXButton submit;
    @FXML
    private Label final_ans;

    private int lives;
    private Word word;
    private Image image;
    private boolean check;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        word = getRandomWord();
        System.out.println(word.getWord_spelling());
        lives = 5;
        check = true;
        String pron = "/" + word.getWord_pronunciation() + "/";
        pronunciation_text.setText(pron);
        answer_text.setOnKeyPressed(new EventHandler<InputEvent>() {
            @Override
            public void handle(InputEvent t) {
                if(t instanceof KeyEvent) {
                    KeyEvent event = (KeyEvent) t;
                    if (event.getCode() == KeyCode.ENTER) {
                        if (check) {
                            try {
                                confirm();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        try {
            image = new Image(new FileInputStream("src/main/resources/pictures/TryToGuess.png"));
            PronImage.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TryAgain(ActionEvent event) throws Exception {
        ChangeScene changeScene = new ChangeScene();
        changeScene.Change("pronunciation.fxml", UserInterface.global);
    }

    public void ReturnBack(ActionEvent event) throws Exception {
        ChangeScene changeScene = new ChangeScene();
        changeScene.Change("ChooseGame.fxml", UserInterface.global);
    }

    public void enter(ActionEvent event) throws Exception {
        if (!check) return;;
        confirm();
    }

    public static Word getRandomWord() {
        Random random = new Random();
        int index = random.nextInt(UserInterface.NormalWord.size());
        Word word = UserInterface.NormalWord.get(index);
        return word;
    }

    public void confirm() throws Exception {
        if (Objects.equals(answer_text.getText(), "")) return;
        if (answer_text.getText().trim().equals(word.getWord_spelling())) {
            image = new Image(new FileInputStream("src/main/resources/pictures/Victory.png"));
            PronImage.setImage(image);
            check = false;
            final_ans.setText(word.getWord_spelling());
            final_ans.setTextFill(Color.web("04FF00"));
        } else {
            lives--;
            if (lives == 4) {
                life5.setVisible(false);
                image = new Image(new FileInputStream("src/main/resources/pictures/WrongAnswer.png"));
                PronImage.setImage(image);
            } else if (lives == 3) {
                life4.setVisible(false);
                image = new Image(new FileInputStream("src/main/resources/pictures/Incorrect.png"));
                PronImage.setImage(image);
            } else if (lives == 2) {
                life3.setVisible(false);
                image = new Image(new FileInputStream("src/main/resources/pictures/2livesLeft.png"));
                PronImage.setImage(image);
            } else if (lives == 1) {
                life2.setVisible(false);
                image = new Image(new FileInputStream("src/main/resources/pictures/LastChance.png"));
                PronImage.setImage(image);
            } else if (lives == 0) {
                life1.setVisible(false);
                image = new Image(new FileInputStream("src/main/resources/pictures/YouLose.png"));
                PronImage.setImage(image);
                check = false;
                final_ans.setText(word.getWord_spelling());
                final_ans.setTextFill(Color.web("#FF0000"));
            }
        }
    }

}
