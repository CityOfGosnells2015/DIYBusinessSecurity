package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by James on 3/04/2015.
 */
public class Checklist {


    /*
        Users answers will correspond to element in the question list
     */
    private List<Question> questList;
    private List<Answer> userAnswer;

    public Checklist(){
    }

    /*
        Get and set
     */
    public void setQuestList(List<Question> questList) {
        this.questList = questList;
    }

    public void setUserAnswer(List<Answer> userAnswer) {
        this.userAnswer = userAnswer;
    }

    /*
        Get
     */
    public List<Answer> getUserAnswer() {
        return userAnswer;
    }

    public List<Question> getQuestList() {
        return questList;
    }

    /*
        Returns A element directly

        (Add error handling for outofbounds exception)
     */
    public Question getQuestionByID(int element){
        return questList.get(element);
    }

    public Answer getAnswerByID(int element){
        return userAnswer.get(element);
    }


}


