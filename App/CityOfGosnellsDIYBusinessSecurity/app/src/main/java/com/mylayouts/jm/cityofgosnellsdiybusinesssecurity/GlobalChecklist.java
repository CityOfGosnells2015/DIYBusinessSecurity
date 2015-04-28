package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.Application;

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

    public Checklist getTheOneChecklist(){
        return theOneChecklist;
    }

    public void setTheOneChecklist(Checklist theOneChecklist){

        this.theOneChecklist=theOneChecklist;
    }

}
