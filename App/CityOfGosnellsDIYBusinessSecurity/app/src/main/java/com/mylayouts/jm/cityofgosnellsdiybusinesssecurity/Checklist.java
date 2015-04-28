package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 3/04/2015.
 */
public class Checklist {


    /*
        Users answers will correspond to element in the question list
     */
    private ArrayList<Question> questList;
    private ArrayList<UserAnswer> userAnswer;
    private int versionNumber;

    public Checklist(){

        questList = new ArrayList<>();
        userAnswer = new ArrayList<>();

    }

    /*
        Get and set
     */
    public void setQuestList(ArrayList<Question> questList) {
        this.questList = questList;
    }

    public void setUserAnswer(ArrayList<UserAnswer> userAnswer) {
        this.userAnswer = userAnswer;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    /*
            Get
         */
    public ArrayList<UserAnswer> getUserAnswer() {
        return userAnswer;
    }

    public ArrayList<Question> getQuestList() {
        return questList;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    /*
            Returns A element directly
    */
    public Question getQuestionByIndex(int element){
        return questList.get(element);
    }

    public UserAnswer getAnswerByIndex(int element){
        return userAnswer.get(element);
    }



    /*
        Sets Answer by ID

        (Element Number for now)
     */

    /**
     * Sets an answer by its element
     *
     * @param element
     * @param answer
     */
    public void setAnswerByID(int element, UserAnswer answer)
    {
        userAnswer.set(element,answer);
    }



}


