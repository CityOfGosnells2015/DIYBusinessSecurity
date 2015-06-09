package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by James on 3/04/2015.
 */
public class UserAnswer implements Serializable{


    /**
     * Defualt Constructor
     *
     * (Current defaults to true)
     */
    public UserAnswer(){
        current = true;
    }

    /**
     * Params Set
     *
     * @param uid
     * @param answer
     *
     * (Current defaults to true)
     */
    public UserAnswer(String uid,Answer answer){
        this.uid = uid;
        this.answer = answer;

        current = true;
    }


    private Answer answer;
    private String uid;
    private boolean current;



    public String getUid() {
        return uid;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public boolean isCurrent() {
        return current;
    }

    /*
                For implementing Serializable
             */
    private static final long serialVersionUID = -3887830328245812248L;

    private void readObject(ObjectInputStream aInputStream)
            throws ClassNotFoundException, IOException {
        aInputStream.defaultReadObject();
    }

    private void writeObject(ObjectOutputStream aOutputStream)
            throws IOException {
        aOutputStream.defaultWriteObject();
    }
}
