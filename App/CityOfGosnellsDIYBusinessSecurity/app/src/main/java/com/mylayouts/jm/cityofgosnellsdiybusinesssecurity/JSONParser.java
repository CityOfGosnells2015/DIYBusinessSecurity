package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

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
    public ArrayList<Link> getEmergencyContacts() throws JSONException {

        /*
            Variables
         */
        ArrayList<Link> contactList = new ArrayList<Link>();
        JSONObject importNumbers = jsonFile.getJSONObject("ImportantPhoneNumbers");
        String keyValue;
        Iterator iterator = importNumbers.keys(); // Loads all the keys (Strings) from the JsonObject

        while(iterator.hasNext()){

            /*
              Get Key and Corresponding
             */
            keyValue = iterator.next().toString();

            contactList.add(new Link(keyValue,importNumbers.getString(keyValue)));

        }

        /*
            Return List
         */
        return  contactList;


    }

    /*
            Reads Notification to a hash map
     */
    public ArrayList<Notification> getNotifications() throws JSONException {

        /*
            Variables
         */
        ArrayList<Notification> notificationList = new ArrayList<Notification>();
        JSONObject element;
        Notification notify;
        JSONArray notificationJSON = jsonFile.getJSONArray("Notifications");

        /*
            Loop for each JSON Object in JSON Array
         */
        for (int index = 0; index < notificationJSON.length(); index++) {

            /*
                Get object at index
             */
            element = notificationJSON.getJSONObject(index);


            //Load into notification object
            notify = new Notification();
            notify.setId(element.getString("id"));
            notify.setPeriod(element.getString("period"));
            notify.setQuestion(element.getString("question"));


            //Add to notifcation list
            notificationList.add(notify);

        }

        /*
            Return List
         */
        return  notificationList;


    }


    /**
     * Reads the suppllied weblinks from the JSon Service
     *
     * @return ArrayList
     * @throws JSONException
     */
    public ArrayList<Link> getWebLinks() throws JSONException {

        /*
            Variables
         */
        ArrayList<Link> webLinkList = new ArrayList<>();
        JSONObject importNumbers = jsonFile.getJSONObject("Websites");
        String keyValue;
        Iterator iterator = importNumbers.keys(); // Loads all the keys (Strings) from the JsonObject
        Link addLink;

        while(iterator.hasNext()){

            /*
              Get Key and Corresponding
             */
            keyValue = iterator.next().toString();

            /*
                Add webpage and Name to link
             */
            addLink = new Link(keyValue);
            addLink.setWebPage(importNumbers.getString(keyValue));

            /*
                Add to array
             */
            webLinkList.add(addLink);

        }

        /*
            Return List
         */
        return  webLinkList;


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
