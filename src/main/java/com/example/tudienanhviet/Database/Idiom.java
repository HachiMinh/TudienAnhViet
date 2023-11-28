package com.example.tudienanhviet.Database;

public class Idiom {
    private String idiom_sentence;
    private Meaning idiom_meaning;

    public Idiom() {}

    public Idiom(String idiom_sentence) {
        this.idiom_sentence = idiom_sentence;
    }

    public void add(Object o) {
        if (o instanceof Meaning) {
            idiom_meaning = (Meaning) o;
        } else {
            if (idiom_meaning != null) idiom_meaning.add(o);
            else {
                System.out.println(idiom_sentence);
            }
        }
    }

    public String print() {
        String ans = " * (Thành ngữ) " + idiom_sentence + "\n";
        ans += idiom_meaning.print();
        return ans;
    }
}
