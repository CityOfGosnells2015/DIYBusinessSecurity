package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import java.util.ArrayList;

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

    /**
     *
     */
    public Checklist(){

        questList = new ArrayList<>();
        userAnswer = new ArrayList<>();
        versionNumber = 0;

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

    /**
     * Sets an answer by its element
     *
     * @param id
     * @param answer
     */
    public boolean setAnswerByID(String id, Answer answer)
    {

        boolean isFound = false;

        for(int index = 0;index<userAnswer.size();index++){

            if (userAnswer.get(index).getUid().equalsIgnoreCase(id)){

                isFound = true;
                userAnswer.get(index).setAnswer(answer);
                break;

            }

        }

        return isFound;


    }

    /**
     *
     * Returns a list of all the Questions in a paticular catagory
     *
     * @param category
     * @return
     * @author James McNeil
     */
    public ArrayList<Question> getQuestionsByCategory(String category){

        ArrayList<Question> questions = new ArrayList<Question>();

        for(Question quest: questList){

            if(quest.getCategory().equalsIgnoreCase(category)){

                questions.add(quest);
            }

        }

        return questions;


    }

    /**
     * Give a list of a all category's within the JSON file
     *
     * @return
     */
    public String[] listCategory(){

        ArrayList<String> strings = new ArrayList<String>();
        String addCat = questList.get(0).getCategory();
        boolean catFound = false;
        String[] returnString;

        //Add initial cat
        strings.add(addCat);

        //Loop t- each question
        for(Question quest: questList){

            for(String catTest: strings){

                if(quest.getCategory().equalsIgnoreCase(catTest))catFound=true;

            }

            if(!catFound) strings.add(quest.getCategory());

            catFound = false;

        }

        returnString = new String[strings.size()];

        for (int index = 0;index < returnString.length;index++){

            returnString[index] = strings.get(index);

        }

        return returnString;

    }

    /**
     * Return a UserAnswer object by Id
     *
     * @param Id
     * @return UserAnswer
     */

    public UserAnswer getAnswerById(String Id){

        for(UserAnswer userAnswer1 : userAnswer){
            if(Id.equals(userAnswer1.getUid())){
                return userAnswer1;
            }
        }
        return null;
    }

    /**
     * Returns a list of Notification in a particular period
     *
     * @param period, listNotification
     * @return notifications
     * @author Gustavo Dias
     */
    public ArrayList<Notification> getNotificationByPeriod(String period, ArrayList<Notification> listNotification) {
        ArrayList<Notification> notifications = new ArrayList<Notification>();

        for (Notification notif : listNotification) {
            if (notif.getPeriod().equalsIgnoreCase(period)) {
                notifications.add(notif);
            }
        }
        return notifications;
    }
}


