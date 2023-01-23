package com.example.q3e;

import java.util.List;

public abstract class Question {
    public abstract List<String> getWrongAnswer();
    public abstract List<String> getCorreAnswer();
    public abstract int getId();
    public abstract String getQuastion();
    public abstract int getNrOfAnswer();

}
