package com.example.englishdictionaryapp.Database;

import java.util.ArrayList;

public class PhrasalVerb {
    private String phrasal_verb;
    private ArrayList<Meaning> Phrasal_Verb_Meanings = new ArrayList<Meaning>();
    private ArrayList<Idiom> Phrasal_Verb_Idioms = new ArrayList<Idiom>();

    public PhrasalVerb() {}

    public PhrasalVerb(String phrasal_verb) {
        this.phrasal_verb = phrasal_verb;
    }

    public void add(Object o, int meaning_for_pos) {
        if (o instanceof Idiom) {
            Phrasal_Verb_Idioms.add((Idiom) o);
        } else if (o instanceof Meaning && meaning_for_pos == 3) {
            Phrasal_Verb_Meanings.add((Meaning) o);
        } else {
            if (meaning_for_pos == 3) {
                Meaning meaning = Phrasal_Verb_Meanings.get(Phrasal_Verb_Meanings.size() - 1);
                meaning.add(o);
                Phrasal_Verb_Meanings.set(Phrasal_Verb_Meanings.size() - 1, meaning);
            } else {
                Idiom idiom = Phrasal_Verb_Idioms.get(Phrasal_Verb_Idioms.size() - 1);
                idiom.add(o);
                Phrasal_Verb_Idioms.set(Phrasal_Verb_Idioms.size() - 1, idiom);
            }
        }
    }

    public String print() {
        String ans = "+ " + phrasal_verb + "\n";
        for (int i = 0; i < Phrasal_Verb_Meanings.size(); i++) {
            ans += Phrasal_Verb_Meanings.get(i).print();
        }
        for (int i = 0; i < Phrasal_Verb_Idioms.size(); i++) {
            ans += Phrasal_Verb_Meanings.get(i).print();
        }
        return ans;
    }
}
