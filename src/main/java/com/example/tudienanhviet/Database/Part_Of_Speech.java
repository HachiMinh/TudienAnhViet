package com.example.tudienanhviet.Database;

import java.util.ArrayList;

public class Part_Of_Speech {
    private ArrayList<Meaning> PoS_Meanings = new ArrayList<Meaning>();
    private ArrayList<Idiom> PoS_Idioms = new ArrayList<Idiom>();
    private ArrayList<Example> PoS_Example = new ArrayList<Example>();
    private String description;

    public Part_Of_Speech() {}

    public Part_Of_Speech(String description) {
        this.description = description;
    }

    public void add(Object o, int meaning_for_word, int example_for_meaning) {
        if (o instanceof Idiom) {
            PoS_Idioms.add((Idiom) o);
        } else if (o instanceof Meaning && meaning_for_word == 1) {
            PoS_Meanings.add((Meaning) o);
        } else if (o instanceof Example && example_for_meaning == 0) {
            PoS_Example.add((Example) o);
        } else if (meaning_for_word == 2) {
            Idiom idiom = PoS_Idioms.get(PoS_Idioms.size() - 1);
            idiom.add(o);
            PoS_Idioms.set(PoS_Idioms.size() - 1, idiom);
        } else if (example_for_meaning == 1) {
            Meaning meaning = PoS_Meanings.get(PoS_Meanings.size() - 1);
            meaning.add(o);
            PoS_Meanings.set(PoS_Meanings.size() - 1, meaning);
        }
    }

    public String print() {
        String ans = "\n- " + description + ".\n";
        for (int i = 0; i < PoS_Example.size(); i++) {
            ans += PoS_Example.get(i).print();
        }
        for (int i = 0; i < PoS_Meanings.size(); i++) {
            ans += PoS_Meanings.get(i).print();
        }
        for (int i = 0; i < PoS_Idioms.size(); i++) {
            ans += PoS_Idioms.get(i).print();
        }
        return ans;
    }
}
