package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

/**
 * Created by James on 3/04/2015.
 *
 * Question contains Data for each question in a checklist
 */
public class Question {

    private String category;
    private String question;
    /*
        private int id;
     */

    public Question(){}

    public Question(String category,String question){

        this.category=category;
        this.question=question;

    }


    /*

    Used later hopefully

    public Question(String catagory,String question,int id)
    {
        this.question=question;
        this.catagory=catagory;
        this.id = id;
    }
    */

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


}
