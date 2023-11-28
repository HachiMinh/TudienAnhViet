package com.example.tudienanhviet.zones;

import com.example.tudienanhviet.UserInterface;
import com.example.tudienanhviet.ChangeScene;
import com.example.tudienanhviet.Database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class HangmanGame implements Initializable{

    @FXML
    ImageView hangman_Image;
    @FXML
    Text guess_text;
    @FXML
    Text lives_text;
    @FXML
    Pane button_pane;



    private int lives;
    private int right;
    private Word w;
    private String hangman_text;
    private String right_text;
    private Image image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            image = new Image(new FileInputStream("src/main/resources/pictures/Hangman0.jpg"));
            hangman_Image.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lives = 7;
        right = 0;
        guess_text.setText("");
        w = getRandomWord();
        System.out.println(w);
        hangman_text = "";
        right_text = "";
        String liveText = "Lives: " + lives;
        lives_text.setText(liveText);
        for (int i = 0; i < w.getWord_spelling().length() * 2; i++) {
            if (i % 2 == 0) {
                hangman_text += "_";
                right_text += w.getWord_spelling().charAt(i/2);
            } else {
                hangman_text += " ";
                right_text += " ";
            }
        }
        String s = w.getWord_spelling().toUpperCase();
        right_text = right_text.toUpperCase();
        if (s.contains("-")) {
            int index = s.indexOf("-");
            while (index != -1) {
                String temp = hangman_text.substring(0, index * 2) + "-"
                        + hangman_text.substring(index * 2 + 1);
                hangman_text = temp;
                guess_text.setText(hangman_text);
                index = s.indexOf('-', index + 1);
                right++;
            }
            if (right == s.length()) {
                button_pane.setDisable(true);
                guess_text.setText(right_text);
            }
        }
        guess_text.setText(hangman_text);

    }

    public static Word getRandomWord() {
        Random random = new Random();
        int index = random.nextInt(UserInterface.dictionary.getSizeofDictionary());
        Word word = UserInterface.dictionary.takeWord(index);
        return word;
    }

    public void clicked(ActionEvent event) throws Exception {
        ((Button) event.getSource()).setDisable(true);
        String character = ((Button)event.getSource()).getText();
        String s = w.getWord_spelling().toUpperCase();
        if (s.contains(character)) {
            int index = s.indexOf(character);
            while (index != -1) {
                String temp = hangman_text.substring(0, index * 2) + character
                        + hangman_text.substring(index * 2 + 1);
                hangman_text = temp;
                guess_text.setText(hangman_text);
                index = s.indexOf(character, index + 1);
                right++;
            }
            if (right == s.length()) {
                button_pane.setDisable(true);
                guess_text.setText(right_text);
                String liveText = "WELL DONE!";
                lives_text.setText(liveText);
                try {
                    image = new Image(new FileInputStream("src/main/resources/pictures/Pom-Pom_Sticker.png"));
                    hangman_Image.setImage(image);
                } catch (Exception e) {}
            }
        } else {
            lives--;
            try {
                if (lives == 6) image = new Image(new FileInputStream("src/main/resources/pictures/Hangman1.jpg"));
                else if (lives == 5) image = new Image(new FileInputStream("src/main/resources/pictures/Hangman2.jpg"));
                else if (lives == 4) image = new Image(new FileInputStream("src/main/resources/pictures/Hangman3.jpg"));
                else if (lives == 3) image = new Image(new FileInputStream("src/main/resources/pictures/Hangman4.jpg"));
                else if (lives == 2) image = new Image(new FileInputStream("src/main/resources/pictures/Hangman5.jpg"));
                else if (lives == 1) image = new Image(new FileInputStream("src/main/resources/pictures/Hangman6.jpg"));
                else if (lives == 0) image = new Image(new FileInputStream("src/main/resources/pictures/Sayori_Hanging.jpg"));
                hangman_Image.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (lives == 0) {
                button_pane.setDisable(true);
                guess_text.setText(right_text);
                String liveText = "NOOB";
                lives_text.setText(liveText);
            } else {
                String liveText = "Lives: " + lives;
                lives_text.setText(liveText);
            }
        }
    }

    public void TryAgain(ActionEvent event) throws Exception {
        ChangeScene changeScene = new ChangeScene();
        changeScene.Change("hangman.fxml", UserInterface.global);
    }

    public void ReturnBack(ActionEvent event) throws Exception {
        ChangeScene changeScene = new ChangeScene();
        changeScene.Change("ChooseGame.fxml", UserInterface.global);
    }
}
