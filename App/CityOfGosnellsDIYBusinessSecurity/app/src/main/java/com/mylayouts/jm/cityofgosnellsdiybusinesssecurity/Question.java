package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

/**
 * Created by James on 3/04/2015.
 *
 * Question contains Data for each question in a checklist
 */
public class Question {

    private String category;
    private String question;
    private String uid;


    public Question(){}

    public Question(String uid,String category,String question){

        this.category=category;
        this.question=question;
        this.uid=uid;

    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getUid() {
        return uid;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



}
