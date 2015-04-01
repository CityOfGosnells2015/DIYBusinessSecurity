package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 041401076 on 1/04/2015.
 */
public class JSONParser {

    private JSONObject jsonFile;

    /*
        Constructor
     */
    public JSONParser(JSONObject jsonFile){
        this.jsonFile=jsonFile;
    }

    public Checklist getChecklist() throws JSONException {

        Checklist parsedList = new Checklist();
        JSONObject element;
        List<Question> questList = null;

        /*
            Get Array from the json fle
         */
        JSONArray checklistJSON = jsonFile.getJSONArray("ChecklistQuestions");

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

        /*
            Add Question list to checklist
         */
        parsedList.setQuestList(questList);

        /*
            Return
         */
        return parsedList;


    }


    /*
            Reads Emergency contacts to a hash map
     */
    public HashMap<String,String> getEmergencyContacts() throws JSONException {

        /*
            Variables
         */
        HashMap<String,String> contactHash = null;
        JSONObject importNumbers = jsonFile.getJSONObject("ImportantPhoneNumbers");
        String keyValue;
        Iterator iterator = importNumbers.keys(); // Loads all the keys (Strings) from the JsonObject

        while(iterator.hasNext()){

            /*
              Get Key and Corresponding
             */
            keyValue = iterator.next().toString();
            contactHash.put(keyValue,importNumbers.getString(keyValue));

        }

        /*
            Return hash map
         */
        return  contactHash;


    }



}
