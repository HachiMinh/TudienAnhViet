package com.example.tudienanhviet.zones;
import java.util.Date;

public class Word_Searched_Info {
    private String word;
    private Date date;

    public Word_Searched_Info(String word, Date date) {
        this.word = word;
        this.date = date;
    }

    public Word_Searched_Info() {}

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
