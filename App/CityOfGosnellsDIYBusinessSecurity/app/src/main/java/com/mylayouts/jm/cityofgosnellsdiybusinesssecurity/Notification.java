package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

/**
 * Created by Gustavo on 24/09/2015.
 */
public class Notification {

    private String question;
    private String id;
    private String period;
    private boolean selected;

    public Notification() {
    }

    public Notification(String question, String period, String id) {
        this.question = question;
        this.period = period;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

