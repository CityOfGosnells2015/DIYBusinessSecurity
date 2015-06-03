package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Global Checklist
 *
 * Stores a Checklist that can be used by all activitys
 *
 * Created by 041401076 on 28/04/2015.
 * @author James McNeil
 */
public class GlobalChecklist extends Application {

    private Checklist theOneChecklist;
    private ArrayList<Link> emergencyContacts;

    public Checklist getTheOneChecklist(){
        return theOneChecklist;
    }

    public void setTheOneChecklist(Checklist theOneChecklist){

        this.theOneChecklist=theOneChecklist;
    }

    public ArrayList<Link> getEmergencyContacts(){return emergencyContacts;}

    public void setEmergencyContacts(ArrayList<Link> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }
}
