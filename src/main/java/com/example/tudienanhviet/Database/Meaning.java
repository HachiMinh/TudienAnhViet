package com.example.tudienanhviet.Database;

import java.util.ArrayList;

public class Meaning {
    private ArrayList<Example> Meaning_Examples = new ArrayList<Example>();
    private String meaning;

    public Meaning() {}

    public Meaning(String meaning) {
        this.meaning = meaning;
    }

    public void add(Object o) {
        if (o instanceof Example) {
            Meaning_Examples.add((Example) o);
        }
    }

    public String print() {
        String ans = "-> " + meaning + "\n";
        for (int i = 0; i < Meaning_Examples.size(); i++) {
            ans += Meaning_Examples.get(i).print();
        }
        return ans;
    }
}
