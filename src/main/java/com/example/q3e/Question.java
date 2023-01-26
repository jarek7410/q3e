package com.example.q3e;

import java.util.List;

public abstract class Question {
    String ans=null;
    public abstract boolean ansIsCorrect();

    public abstract List<String> getWrongAnswer();
    public abstract List<String> getCorreAnswer();
    public abstract int getId();
    public abstract String getQuastion();
    public abstract int getNrOfAnswer();

}
