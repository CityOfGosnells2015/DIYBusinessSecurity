package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by 041401076 on 28/04/2015.
 */
public class FileStore {


    /**
     *
     */
    public FileStore(){}


    /**
     * Saves Users answers to Internal File
     *
     * @param userAnswers
     * @param fileName
     */
    public void saveUserFile(ArrayList<UserAnswer> userAnswers, String fileName, Context appContext) throws IOException {



        File file = new File(appContext.getFilesDir().getPath().toString() + "/" +fileName);

        /*
            Create file if it doesn't exist
         */
        if (!file.exists()){
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(userAnswers);

        fos.close();
        oos.close();

    }

    /**
     *
     * Returns a ArrayList of saved answers
     *
     *
     * @param fileName
     * @param appContext
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ArrayList<UserAnswer> loadUserFile(String fileName, Context appContext) throws IOException, ClassNotFoundException{

        ArrayList<UserAnswer> answerArray;
        File file = new File(appContext.getFilesDir().getPath().toString() + "/" + fileName);

        if (file.exists()) {

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            answerArray = (ArrayList) (ois.readObject());

            fis.close();
            ois.close();

            return answerArray;

        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * Saves Log checklist to Internal File
     *
     * @param logChecklist
     * @param fileName
     */
    public void saveLogFile(String logChecklist, String fileName, Context appContext) throws IOException {

        File file = new File(appContext.getFilesDir().getPath() + "/" +fileName);

        /*
            Create file if it doesn't exist
         */
        if (!file.exists()){
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(logChecklist);

        fos.close();
        oos.close();

    }

    /**
     * Returns a ArrayList of saved log checklist
     * @param fileName
     * @param appContext
     * @return arrayLogChecklist
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ArrayList<String> loadLogFile(String fileName, Context appContext) throws IOException, ClassNotFoundException{

        ArrayList arrayLogChecklist = new ArrayList();
        File file = new File(appContext.getFilesDir().getPath() + "/" + fileName);

        if (file.exists()) {

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object object = ois.readObject();
            arrayLogChecklist.add((String)object);

            fis.close();
            ois.close();

            return arrayLogChecklist;

        } else {
            throw new FileNotFoundException();
        }
    }

}
