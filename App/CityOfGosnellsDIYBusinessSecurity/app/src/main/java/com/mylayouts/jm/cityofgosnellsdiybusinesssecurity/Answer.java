package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

/**
 * Created by James on 3/04/2015.
 */
public enum Answer {

    Y("Yes"), N("No"), NA("Not Applicable"), U("Unanswered");

    private final String toText;

    Answer(String toText){
        this.toText = toText;
    }

    String toText(){
        return toText;
    }

}
