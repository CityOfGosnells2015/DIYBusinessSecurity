package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

/**
 * Created by Gustavo on 24/09/2015.
 */
public class Notification {

    private String question;
    private String period;

    public Notification() {
    }

    public Notification(String question, String period) {
        this.question = question;
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}

