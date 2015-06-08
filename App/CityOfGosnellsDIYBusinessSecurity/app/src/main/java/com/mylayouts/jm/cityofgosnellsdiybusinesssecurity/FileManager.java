package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Gustavo
 * Class which will be responsible to manage any method to access any file.
 */
public class FileManager {
    //Variables
    private Context context;
    private String fileName;

    public FileManager() {
    }

    /**
     * Method which read all the data from MyImportantLinks.txt
     *
     * @param mContext - Which activity is calls this method
     * @return - ArrayList with all data from the file
     */
    public ArrayList<Link> readFile(Context mContext, String mFileName){

        ArrayList<Link> linkList = new ArrayList();
        fileName = mFileName;

        try {
            context = mContext;
            String line;

            InputStream inFile = context.openFileInput(fileName);
            BufferedReader brFile = new BufferedReader(new InputStreamReader(inFile));

            while ((line = brFile.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(line,",");

                Link objLink = new Link();
                objLink.setName(token.nextToken());
                objLink.setPhone(token.nextToken());
                objLink.setWebPage(token.nextToken());

                linkList.add(objLink);
            }
            brFile.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return linkList;
    }

    public boolean writeOnFile(Link objLink, Context mContext, String mFileName){
        boolean result = false;
        context = mContext;
        fileName = mFileName;

        try {
            FileOutputStream fOut = context.openFileOutput(fileName, Context.MODE_APPEND);
            fOut.write(objLink.toString().getBytes());
            fOut.close();

            result = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }


    public boolean deleteFile(Context mContext){
        boolean result = false;
        context = mContext;

        try {
            context.deleteFile(fileName);
            result = true;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

}
