package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 041401076 on 1/04/2015.
 *
 * @author James McNeil
 */
public class JSONParser {

    private JSONObject jsonFile;

    /**
     *
     * @param jsonFile
     */
    public JSONParser(JSONObject jsonFile){
        this.jsonFile=jsonFile;
    }

    public JSONParser(){}


    public void setJsonFile(JSONObject jsonFile) {
        this.jsonFile = jsonFile;
    }

    public JSONObject getJsonFile() {
        return jsonFile;
    }

    /**
     * Returns a checklist
     * -- Updated 29/4 Includes UID from json File
     * -- Updated 29/4 Include Version Number
     * @return
     * @throws JSONException
     */
    public Checklist getChecklist() throws JSONException {

        Checklist parsedList = new Checklist();
        JSONObject element;
        ArrayList<Question> questList = new ArrayList<Question>();

        /*
            Get The Version Number
         */
        parsedList.setVersionNumber(Integer.parseInt(jsonFile.getString("VersionNumber")));


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
                Populate Question ---
             */
            addQuestion.setCategory(element.getString("category"));
            addQuestion.setQuestion(element.getString("question"));
            addQuestion.setUid(element.getString("id"));

            ///LOG CAT
            Log.d("getChecklist TEST", "Got Here createChecklist");

            ///LOG CAT
            Log.d("getChecklist TEST", "" + addQuestion.getUid());

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
        HashMap<String,String> contactHash = new HashMap<String,String>();
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

    /**
     *
     * Converts the string from the URL to a JSONObject
     *
     * @param JSONString
     * @return JSONObject
     */
    public JSONObject parseJSONString(String JSONString) throws JSONException {

        return new JSONObject(JSONString);

    }





}
