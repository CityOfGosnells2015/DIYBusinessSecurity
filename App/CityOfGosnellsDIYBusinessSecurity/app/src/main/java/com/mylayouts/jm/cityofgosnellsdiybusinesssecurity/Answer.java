package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

/**
 * Created by 041401076 on 28/04/2015.
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
