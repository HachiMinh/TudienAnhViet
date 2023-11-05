package com.example.englishdictionaryapp.Database;

public class Example {
    private String Example_Sentence;
    private String Example_Meaning;

    public Example() {}

    public Example(String example) {
        String[] items = example.split("\\+", 2);
        Example_Sentence = items[0];
        String items1 = "";
        if(items.length > 1) {
            items1 = items[1];
        }
        Example_Meaning = items1;
    }

    public String print() {
        String ans = "  Eg: " + Example_Sentence + ": " + Example_Meaning + "\n";
        return ans;
    }
}
