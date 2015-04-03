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

    /*
        Populates Checklist from JSON Array
     */

    public boolean populateChecklist(JSONArray checklistJSON){

        JSONObject element;


        try {
        /*
            Loop for each JSON Object in JSON Array
         */
            for (int index = 0; index < checklistJSON.length(); index++) {

            /*
                Get object at index
             */
                element = checklistJSON.getJSONObject(index);

            /*
                Create new Question
             */
                Question addQuestion = new Question();

            /*
                Populate Question
             */

                addQuestion.setCategory(element.getString("question"));
                addQuestion.setQuestion(element.getString("category"));
                //addQuestion.setId(element.getInt("What ever the id is called, probably id I imagine??"));

            /*
                Add to List
             */
                questList.add(addQuestion);

            }

        }catch(JSONException ex){

            return false;

        }

        return true;


    }
}


