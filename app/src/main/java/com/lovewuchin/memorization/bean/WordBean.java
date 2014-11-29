package com.lovewuchin.memorization.bean;

/**
 * @author Chin
 * @since 2014/11/27
 */
public class WordBean {

    private String word;
    private String symbol;
    private String translation;
    private String pronunciation;
    private String example;
    private String wordList;
    private int studyCount;
    private int mistakeCount;
    private int level;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getWordList() {
        return wordList;
    }

    public void setWordList(String wordList) {
        this.wordList = wordList;
    }

    public int getStudyCount() {
        return studyCount;
    }

    public void setStudyCount(int studyCount) {
        this.studyCount = studyCount;
    }

    public int getMistakeCount() {
        return mistakeCount;
    }

    public void setMistakeCount(int mistakeCount) {
        this.mistakeCount = mistakeCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
