package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.Application;

import java.util.ArrayList;

/**
 * Global Checklist
 *
 * Stores Global Variable to be used by multiple activites in the application
 *
 * theOneChecklist:  Checklist that can be used by all activity
 * emergencyContacts: Stores phonenumbers.
 * webLinks: Stores weblinks.
 *
 * Created by 041401076 on 28/04/2015.
 * @author James McNeil
 */
public class GlobalChecklist extends Application {

    private Checklist theOneChecklist;
    private ArrayList<Link> emergencyContacts;
    private ArrayList<Link> webLinks;
    private ArrayList<Notification> notifications;

    /*
        Get and Set for Checklist
     */
    public Checklist getTheOneChecklist(){
        return theOneChecklist;
    }

    public void setTheOneChecklist(Checklist theOneChecklist){

        this.theOneChecklist=theOneChecklist;
    }

    /*
        Get and set for Links
     */
    public ArrayList<Link> getEmergencyContacts(){return emergencyContacts;}

    public void setEmergencyContacts(ArrayList<Link> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    /*
        Get and set for Notifications
     */
    public ArrayList<Notification> getNotifications(){return notifications;}

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    /*
        Get and set Weblinks
     */

    public ArrayList<Link> getWebLinks() {
        return webLinks;
    }

    public void setWebLinks(ArrayList<Link> webLinks) {
        this.webLinks = webLinks;
    }
}
