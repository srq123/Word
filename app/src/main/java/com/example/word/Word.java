package com.example.word;

public class Word {
    private String word;
    private  String mean;
    private  String example;
    public Word(String word, String mean, String example){
        this.word = word;
        this.mean = mean;
        this.example = example;
    }
    public void setWord(String word){
        this.word = word;
    }
    public String getWord(){
        return word;
    }
    public void SetMean(String mean){
        this.mean = mean;
    }
    public String getMean(){
        return mean;
    }
    public void setExample(String Example){
        this.example = example;
    }
    public String getExample(){
        return example;
    }
    public String toSting(){
        return word + mean + example;
    }
}
