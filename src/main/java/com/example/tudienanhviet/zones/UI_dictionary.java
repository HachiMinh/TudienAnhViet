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
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class UI_dictionary implements Initializable {

    @FXML
    private TextArea result;
    @FXML
    private ComboBox search;

    private String word;

    final static Dictionaries d = UserInterface.dictionary;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //search.setPromptText("Search");
        for(int i = 0; i < UserInterface.dictionary.getSizeofDictionary();i++) {
            search.getItems().add(UserInterface.dictionary.takeWord(i));
            new AutoCompleteComboBoxListener<>(search);
        }
    }

    public void confirm(ActionEvent event) throws Exception {
        word = (String) search.getSelectionModel().getSelectedItem();
        if (Objects.equals(word, null)) return;
        for(int i = 0; i < d.getSizeofDictionary(); i++) {
            if(word.equals(d.takeWord(i).getWord_spelling())) {
                Word w = d.takeWord(i);
                String ans = w.print();
                result.setText(ans);
                break;
            }
        }
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
                    for(int i = 0; i < d.getSizeofDictionary(); i++) {
                        if(word.equals(d.takeWord(i).getWord_spelling())) {
                            Word w = d.takeWord(i);
                            String ans = w.print();
                            result.setText(ans);
                        }
                    }
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


