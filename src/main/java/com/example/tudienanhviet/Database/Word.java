package com.example.tudienanhviet.Database;

import java.util.ArrayList;

public class Word {
    private String word_spelling;
    private String word_pronunciation;
    private ArrayList<Part_Of_Speech> Word_Parts_of_Speech = new ArrayList<Part_Of_Speech>();
    private ArrayList<Meaning> Word_Meanings = new ArrayList<Meaning>();
    private ArrayList<PhrasalVerb> Word_Phrasal_Verbs = new ArrayList<PhrasalVerb>();

    public Word() {}

    public Word(String word_spelling, String word_pronunciation) {
        this.word_spelling = word_spelling;
        this.word_pronunciation = word_pronunciation;
    }

    public void add(Object o, int meaning_for_word, int idiom_for_pos, int example_for_meaning) {
        if (o instanceof Part_Of_Speech) {
           Word_Parts_of_Speech.add((Part_Of_Speech) o);
        } else if (o instanceof PhrasalVerb) {
            Word_Phrasal_Verbs.add((PhrasalVerb) o);
        } else if (o instanceof Meaning && meaning_for_word == 0) {
            Word_Meanings.add((Meaning) o);
        } else if (o instanceof Example && meaning_for_word == 0) {
            Meaning meaning = Word_Meanings.get(Word_Meanings.size() - 1);
            meaning.add(o);
            Word_Meanings.set(Word_Meanings.size() - 1, meaning);
        } else {
            if (idiom_for_pos == 1) {
                if (Word_Parts_of_Speech.isEmpty()) System.out.println(word_spelling + " haha");
                else {
                Part_Of_Speech pos = Word_Parts_of_Speech.get(Word_Parts_of_Speech.size() - 1);
                pos.add(o, meaning_for_word, example_for_meaning);
                Word_Parts_of_Speech.set(Word_Parts_of_Speech.size() - 1, pos); }
            } else if (idiom_for_pos == 0) {
                PhrasalVerb pv = Word_Phrasal_Verbs.get(Word_Phrasal_Verbs.size() - 1);
                pv.add(o, meaning_for_word);
                Word_Phrasal_Verbs.set(Word_Phrasal_Verbs.size() - 1, pv);
            }
        }
    }

    public String toString() {
        return word_spelling;
    }

    public String getWord_spelling() {
        return word_spelling;
    }

    public String getWord_pronunciation() {
        return word_pronunciation;
    }
    public void addMeaning(String text) {
        Word_Meanings.add(new Meaning(text));
    }

    public String print() {
        String ans = word_spelling + "\t";
        if (!word_pronunciation.isBlank()) ans += "[" + word_pronunciation + "]";
        ans += "\n";
        for (int i = 0; i < Word_Meanings.size(); i++) {
            ans += Word_Meanings.get(i).print();
        }
        for (int i = 0; i < Word_Parts_of_Speech.size(); i++) {
            ans += Word_Parts_of_Speech.get(i).print();
        }
        for (int i = 0; i < Word_Phrasal_Verbs.size(); i++) {
            ans += Word_Phrasal_Verbs.get(i).print();
        }
        return ans;
    }
}
