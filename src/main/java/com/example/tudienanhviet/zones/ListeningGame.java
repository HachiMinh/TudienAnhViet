package com.example.tudienanhviet.zones;

import com.example.tudienanhviet.ChangeScene;
import com.example.tudienanhviet.Database.Word;
import com.example.tudienanhviet.UserInterface;
import com.example.tudienanhviet.WordListening;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Random;

public class ListeningGame implements Initializable {

    @FXML
    private Button choice_A;
    @FXML
    private Button choice_B;
    @FXML
    private Button choice_C;
    @FXML
    private Button choice_D;
    @FXML
    private ImageView LisGameImg;

    private int index;
    private boolean check = true;
    private Image image;

    private ArrayList<Word> words;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final int[] ints = new Random().ints(1, UserInterface.dictionary.getSizeofDictionary()).distinct().limit(4).toArray();
        words = new ArrayList<Word>();
        for (int i = 0; i < 4; i++) {
            words.add(UserInterface.dictionary.takeWord(ints[i]));
        }
        try {
            image = new Image(new FileInputStream("src/main/resources/pictures/pom-pom_question.png"));
            LisGameImg.setImage(image);
        } catch (Exception e) { e.printStackTrace();}
        check = true;
        choice_A.setText(words.get(0).getWord_spelling());
        choice_B.setText(words.get(1).getWord_spelling());
        choice_C.setText(words.get(2).getWord_spelling());
        choice_D.setText(words.get(3).getWord_spelling());
        index = new Random().nextInt(4);
        try { listen(); } catch (Exception e) {}

        //System.out.println(index);
    }

    private Button ButtonNum(int i) {
        if (i == 0) return choice_A;
        if (i == 1) return choice_B;
        if (i == 2) return choice_C;
        return choice_D;
    }


    public void TryAgain(ActionEvent event) throws Exception {
        ChangeScene changeScene = new ChangeScene();
        changeScene.Change("listen.fxml", UserInterface.global);
    }

    public void ReturnBack(ActionEvent event) throws Exception {
        ChangeScene changeScene = new ChangeScene();
        changeScene.Change("ChooseGame.fxml", UserInterface.global);
    }

    public void confirm(ActionEvent event) throws Exception {
        if (!check) return;
        String choice = ((Button)event.getSource()).getAccessibleText();
        int i = 0;
        //System.out.println(choice);
        if (choice.equals("0")) i = 0;
        else if (choice.equals("1")) i = 1;
        else if (choice.equals("2")) i = 2;
        else if (choice.equals("3")) i = 3;
        //System.out.println(i);
        if (i == index) {
            ButtonNum(index).setStyle("-fx-background-color: #04FF00");
            check = false;
            image = new Image(new FileInputStream("src/main/resources/pictures/pom-pom_like.png"));
            LisGameImg.setImage(image);
        } else {
            ButtonNum(index).setStyle("-fx-background-color: #04FF00");
            ButtonNum(i).setStyle("-fx-background-color: #FF0000");
            check = false;
            image = new Image(new FileInputStream("src/main/resources/pictures/MarchCry.png"));
            LisGameImg.setImage(image);
        }
    }

    public void listening(ActionEvent event) throws Exception {
        listen();
    }

    public void listen() throws Exception {
        WordListening wordListening = new WordListening(words.get(index).getWord_spelling());
        wordListening.valueProperty().addListener(
                (observable, oldValue, newValue) -> newValue.start());
        System.out.println(words.get(index).getWord_spelling());
        Thread thread = new Thread(wordListening);
        thread.setDaemon(true);
        thread.start();
    }
}
