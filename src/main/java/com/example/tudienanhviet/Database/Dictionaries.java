package com.example.tudienanhviet.Database;

import java.util.ArrayList;
import java.io.*;
public class Dictionaries {

    ArrayList<Word> List_of_words = new ArrayList<Word>();
    Word w;
    Meaning mn;
    Part_Of_Speech pos;
    Idiom idi;
    Example e;
    PhrasalVerb pv;

    public Dictionaries(){
        BufferedReader br;
        try {
            FileInputStream fstream = new FileInputStream("src\\main\\java\\com\\example\\tudienanhviet\\Database\\anhviet109K.txt");
            br = new BufferedReader(new InputStreamReader(fstream));

        String st;
        int count = 0;
        boolean check = true;
        int meaning_for_word = 0;
        int example_for_meaning = 0;
        int idiom_for_pos = 0;
        while (!(st = br.readLine()).equals("#")) {
            if (st.isEmpty()) {
                List_of_words.add(w);
                count++;
            } else if(st.contains("@")) {
                st = st.replaceAll("[\uFEFF-\uFFFF]", "");
                String[] items = st.split("/",0);
                items[0] = items[0].trim();
                if(items[0].equals("@1 to 1 relationship")) {
                    break;
                }
                items[0] = items[0].substring(1);
                String items1 = "";
                if(items.length > 1) {
                    items1 = items[1].trim();
                    w = new Word(items[0], items1);
                    meaning_for_word = 0;
                } else {
                    pv = new PhrasalVerb(items[0]);
                    meaning_for_word = 3;
                    idiom_for_pos = 0;
                    example_for_meaning = 1;
                    w.add(pv, meaning_for_word, idiom_for_pos, example_for_meaning);
                }
            } else if(st.startsWith("* ")) {
                pos = new Part_Of_Speech(st.substring(3));
                meaning_for_word = 1;
                idiom_for_pos = 1;
                example_for_meaning = 0;
                w.add(pos, meaning_for_word, idiom_for_pos, example_for_meaning);
            } else if (st.startsWith("!")) {
                idi = new Idiom(st.substring(1));
                meaning_for_word = 2;
                example_for_meaning = 1;
                w.add(idi, meaning_for_word, idiom_for_pos, example_for_meaning);
            } else if (st.startsWith("- ")) {
                mn = new Meaning(st.substring(2));
                example_for_meaning = 1;
                w.add(mn, meaning_for_word, idiom_for_pos, example_for_meaning);
            } else if (st.startsWith("=")) {
                e = new Example(st.substring(1));
                w.add(e, meaning_for_word, meaning_for_word, example_for_meaning);
            }
        }
        //System.out.println(count);
    } catch (Exception e){
            System.out.print("Có lỗi");
        }
    }
    public int getSizeofDictionary() {
        return List_of_words.size();
    }
    public Word takeWord(int index) {
        return List_of_words.get(index);
    }

    public void addword(Word w) {
        List_of_words.add(w);
    }

    public ArrayList<Word> normalWord() {
        ArrayList<Word> nw = new ArrayList<Word>();
        for (int i = 0; i < List_of_words.size(); i++) {
            String s = List_of_words.get(i).getWord_spelling();
            if (s.indexOf(' ') == -1 && s.indexOf('-') == -1) {
                nw.add(List_of_words.get(i));
            }
        }
        return nw;
    }
}
