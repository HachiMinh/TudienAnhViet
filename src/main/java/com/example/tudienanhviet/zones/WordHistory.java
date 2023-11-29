package com.example.tudienanhviet.zones;

import com.example.tudienanhviet.ChangeScene;
import com.example.tudienanhviet.UserInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.ZoneId;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class WordHistory implements Initializable {


    @FXML
    private TableView<Word_Searched_Info> history_table;
    @FXML
    private TableColumn<Word_Searched_Info, Date> date_search;
    @FXML
    private TableColumn<Word_Searched_Info, String> history_word;

    private ObservableList<Word_Searched_Info> word_list;

    Connection connection = null;
    PreparedStatement prepare = null;
    ResultSet resultset = null;

    public final String history_path = "jdbc:sqlite:src/main/resources/WordHistory.sqlite";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        word_list = FXCollections.observableArrayList();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(history_path);
            prepare = connection.prepareStatement("SELECT * FROM History");
            resultset = prepare.executeQuery();

            while (resultset.next()) {
                String word = resultset.getString(1);
                Date date = resultset.getDate(2);
                word_list.add(new Word_Searched_Info(word, date));
            }
        } catch (Exception e) {}
        finally {
            if (resultset != null) {
                try {
                    resultset.close();
                } catch (Exception e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {}
            }
        }
        Collections.reverse(word_list);
        date_search.setCellValueFactory(new PropertyValueFactory<Word_Searched_Info, Date>("date"));
        history_word.setCellValueFactory(new PropertyValueFactory<Word_Searched_Info, String>("word"));
        history_table.setItems(word_list);

        history_table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Word_Searched_Info>() {
            @Override
            public void changed(ObservableValue<? extends Word_Searched_Info> observableValue, Word_Searched_Info wordSearchedInfo, Word_Searched_Info t1) {
                String word = history_table.getSelectionModel().getSelectedItem().getWord();
                UI_dictionary.setWord(word);
                UI_dictionary.history_check = true;
                ChangeScene changeScene = new ChangeScene();
                try {
                    changeScene.Change("searchingzone.fxml", UserInterface.global);
                } catch (Exception e) {}
            }
        });
    }

    public static void AddtoSQL(String word) {
        Connection connect = null;
        PreparedStatement psInsert = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:src/main/resources/WordHistory.sqlite");
            psInsert = connect.prepareStatement("INSERT INTO History VALUES (?, ?)");
            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            psInsert.setString(1, word);
            psInsert.setDate(2, sqlDate);
            psInsert.executeUpdate();
        } catch (Exception e) {}
        finally {
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (Exception e) {}
            }
            if (connect != null) {
                try {
                    connect.close();
                } catch (Exception e) {}
            }
        }
    }

    public void ReturnBack(ActionEvent event) throws Exception {
        UI_dictionary.search_global.getChildren().clear();
        UI_dictionary.search_global.setVisible(false);
    }
}
