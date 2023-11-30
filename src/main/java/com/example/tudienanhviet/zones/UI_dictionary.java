package com.example.tudienanhviet.zones;

import com.example.tudienanhviet.Database.*;
import com.example.tudienanhviet.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;


public class UI_dictionary implements Initializable {

    @FXML
    private TextArea result;
    @FXML
    private ComboBox search;
    @FXML
    private AnchorPane search_root;

    public static AnchorPane search_global = new AnchorPane();
    public static boolean history_check = false;
    private static String word = "";
    Connection connection = null;
    PreparedStatement prepare = null;
    ResultSet resultset = null;
    public final String addword_path = "jdbc:sqlite:src/main/resources/AddWord.sqlite";

    static Dictionaries d;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_global = search_root;
        search_global.setVisible(false);
        d = new Dictionaries();
        //System.out.println(UserInterface.dictionary.getSizeofDictionary());
        addDatabase();
        for(int i = 0; i < d.getSizeofDictionary();i++) {
            search.getItems().add(d.takeWord(i));
            new AutoCompleteComboBoxListener<>(search);
        }
        if (!word.isBlank()) enter();
    }

    public void addDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(addword_path);
            prepare = connection.prepareStatement("SELECT * FROM MyWord");
            resultset = prepare.executeQuery();

            while (resultset.next()) {
                String word = resultset.getString(1);
                String meaning = resultset.getString(2);
                Word w = new Word(word, "");
                w.addMeaning(meaning);
                d.addword(w);
            }
        } catch (Exception e) {}
        finally {
            if (resultset != null) {
                try {
                    resultset.close();
                } catch (Exception e) {}
            }
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (Exception e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {}
            }
        }
    }

    public static void setWord(String s) {
        word = s;
    }

    public void enter() {
        if (Objects.equals(word, null)) return;
        for(int i = 0; i < d.getSizeofDictionary(); i++) {
            if(word.equals(d.takeWord(i).getWord_spelling())) {
                Word w = d.takeWord(i);
                String ans = w.print();
                result.setText(ans);
                break;
            }
        }
        if (!history_check) {
            WordHistory.AddtoSQL(word);
        }
        history_check = false;
    }

    public void confirm(ActionEvent event) throws Exception {
        word = (String) search.getSelectionModel().getSelectedItem();
        enter();
    }

    public void history_view(ActionEvent event) throws Exception {
        search_global.setVisible(true);
        ChangeScene changeScene = new ChangeScene();
        changeScene.Change("word_history.fxml",search_global);
    }

    public void addword_view(ActionEvent event) throws Exception {
        ChangeScene changeScene = new ChangeScene();
        changeScene.Change("addword.fxml",UserInterface.global);
    }

    public void listening(ActionEvent event) throws Exception {
        if (Objects.equals(word, null)) return;
        WordListening wordListening = new WordListening(word);
        wordListening.valueProperty().addListener(
                (observable, oldValue, newValue) -> newValue.start());
        System.out.println(word);
        Thread thread = new Thread(wordListening);
        thread.setDaemon(true);
        thread.start();
    }

    public class AutoCompleteComboBoxListener<T> implements EventHandler<InputEvent> {

        private ComboBox comboBox;
        private StringBuilder sb;
        private ObservableList<T> data;
        private boolean moveCaretToPos = false;
        private int caretPos;

        public AutoCompleteComboBoxListener(final ComboBox comboBox) {
            this.comboBox = comboBox;
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

        @Override
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
                    word = (String) search.getSelectionModel().getSelectedItem();
                    enter();
                    return;
                }

                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                        || event.isControlDown() || event.getCode() == KeyCode.HOME
                        || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                    return;
                }
            }

            ObservableList list = FXCollections.observableArrayList();
            if (AutoCompleteComboBoxListener.this.comboBox
                    .getEditor().getText().toLowerCase() == "") {
                //search.setPromptText("Search");
                list = data;
            } else {
                for (int i=0; i<data.size() & list.size() < 30; i++) {
                    if(data.get(i).toString().toLowerCase().startsWith(
                            AutoCompleteComboBoxListener.this.comboBox
                                    .getEditor().getText().toLowerCase())) {
                        list.add(data.get(i));
                    }
                }
            }
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
}


