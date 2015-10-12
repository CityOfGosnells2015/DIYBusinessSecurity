package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Welcome/Splash Screen
 * <p/>
 * James McNeil 30/03/2015
 */
public class WelcomeActivity extends Activity implements View.OnClickListener {

    Button firstTimeClick;
    SharedPreferences prefs;
    ArrayList<Answer> userAnswers;
    Checklist theOneChecklist;
    GlobalChecklist globalChecklist;

    /*
    URL Of Checklist Data to be retrieved.
    */
    private final static String CHECKLIST_URL = "http://www.gosnells.wa.gov.au/feed.rss?listname=Security%20Audit%20Checklist";
    private final static String FILE_NAME = "answers.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Create checklist
        globalChecklist = (GlobalChecklist) getApplication();

        //Create Checklist
        theOneChecklist = new Checklist();

        prefs = getSharedPreferences("AppInfo", MODE_PRIVATE);
        prefs.edit().putBoolean("VersionChanged", false).commit();

        //Initialise Button and add listener
        firstTimeClick = (Button) findViewById(R.id.btnFirstClick);
        firstTimeClick.setOnClickListener(this);
        firstTimeClick.setEnabled(false);

        if (isOnline()) {
            DownloadJSONTask task = new DownloadJSONTask();
            task.execute(new String[]{CHECKLIST_URL});
        } else {

            /*
                Promt user to connect to network or exit application
             */
            noNetworkErrorDialog();
            Log.d("Not Online", "NOT CONNECTED TO THE INTERNET");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRestart() {
        super.onRestart();

        if (isOnline()) {
            DownloadJSONTask task = new DownloadJSONTask();
            task.execute(new String[]{CHECKLIST_URL});
        } else {

            /*
                Promt user to connect to network or exit application
             */
            noNetworkErrorDialog();
            Log.d("Not Online", "NOT CONNECTED TO THE INTERNET");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnFirstClick) {

            //If app is being used for first time]
            if (prefs.getBoolean("firstrun", true)) {

                //First Time Run Code Goes Here
                prefs.edit().putBoolean("firstrun", false).commit(); // Sets firstrun to false


                //Go to Letter
                Intent intent = new Intent(this, MayorLetter.class);
                startActivity(intent);


            } else {

                // != First time run code here

                //Go to Menu
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            }

        }

    }


    /**
     * @author James McNeil
     */
    private class DownloadJSONTask extends AsyncTask<String, Void, String> {

        /**
         * @param urls
         * @return
         */
        @Override
        protected String doInBackground(String... urls) {

            String response = "";

            for (String url : urls) {

                /*
                    Add Code to fetch JSON String from URL Provided by the city of Gosnells
                 */
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {


                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    StringBuilder sBuilder = new StringBuilder();

                    String line = null;
                    while ((line = buffer.readLine()) != null) {
                        sBuilder.append(line + "\n");
                    }

                    /*
                        Close Stream + Get String
                     */
                    content.close();
                    response = sBuilder.toString();


                } catch (ClientProtocolException e) {
                    Log.e("ClientProtocolException", e.getMessage());
                } catch (IOException e) {
                    Log.e("IOException", e.getMessage());
                }


            }
            return response;
        }

        /*
         After JSON String is retrieved Wait and then move to Welcome Activity
         */
        @Override
        protected void onPostExecute(String result) {

            try {
                //Create Json Parses
                JSONObject jsonObject = new JSONObject(result);
                JSONParser jsonParser = new JSONParser(jsonObject);


                //Get Checklist questions from parser
                theOneChecklist.setQuestList(jsonParser.getChecklist().getQuestList());

                //Set Emergency contact
                globalChecklist.setEmergencyContacts(jsonParser.getEmergencyContacts());

                //Set weblinks
                globalChecklist.setWebLinks(jsonParser.getWebLinks());

                //Set Notification
                globalChecklist.setNotifications(jsonParser.getNotifications());

                /*
                    Splash Screen will always be visible for at least 3 seconds
                    sleep( time in milliseconds)
                 */
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Log.e("Interrupted", "" + e.getMessage());
            } catch (JSONException e) {
                Log.e("JSON Exception", e.getMessage());
            }

            /*
                Finishes Processing Checklist
                (See createChecklist for more details!!)
             */
            createChecklist();

            /*
                Set Button to Enabled
             */
            firstTimeClick.setBackgroundResource(R.drawable.btn_start);
            firstTimeClick.setEnabled(true);

        }
    }

    /**
     * Returns Array Of "Unanswered" answers
     *
     * @return
     * @author James McNeil
     */
    private ArrayList<UserAnswer> createNewAnswerList() {

        ArrayList<UserAnswer> userAnswers = new ArrayList<UserAnswer>();
        UserAnswer addedAnswer;

        for (int index = 0; index < theOneChecklist.getQuestList().size(); index++) {

            addedAnswer = new UserAnswer(theOneChecklist.getQuestionByIndex(index).getUid(), Answer.U);
            userAnswers.add(addedAnswer);

        }

        return userAnswers;

    }

    /**
     * Checks for an internet Connection
     * <p/>
     * (From Tutorials)
     *
     * @return Boolean
     */
    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else return false;

    }

    /**
     * No Network Propmt
     * <p/>
     * Propmts User to connect to the intertnet if they are not
     */
    protected void noNetworkErrorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Check Internet Connection is on")
                .setTitle("No Network")
                .setCancelable(false)
                /*
                    Go to network settings
                 */
                .setPositiveButton("Network Setting",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(i);
                            }
                        }
                )

                /*
                    Exits app if user
                 */
                .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                WelcomeActivity.this.finish();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void createChecklist() {

        /*
            Load Users Answers from file
         */
        //Initialise File
        FileStore fileStore = new FileStore();

        //Create File
        File answerFile = new File(getApplicationContext().getFilesDir().getPath().toString() + "/" + FILE_NAME);
        try {

            ArrayList<UserAnswer> userAnswers;

            //Version Change
            if (prefs.getInt("VersionNumber", 0) != theOneChecklist.getVersionNumber()) {

                prefs.edit().putBoolean("VersionChanged", true).commit();

            }

            prefs.edit().putInt("VersionNumber", theOneChecklist.getVersionNumber()).commit();

            /*
                If no current user file exists

                Creates new file and writes to file answers
                that are initialised to 'U'
             */
            if (!answerFile.exists()) {

                userAnswers = createNewAnswerList();
                theOneChecklist.setUserAnswer(userAnswers);
                fileStore.saveUserFile(userAnswers, FILE_NAME, this.getApplicationContext());


            } else {

                //Load Users Answers file
                userAnswers = fileStore.loadUserFile(FILE_NAME, this.getApplicationContext());

                if (prefs.getBoolean("VersionChanged", false)) {

                    boolean isFound;


					/*
                        Finds all questions in The checklist loaded from the JSON and compares
					    them to the users saved answers

					    If there is a new question in the checklist a new answer will be added to
					    the users answers and initialised to 'U' (Unanswered)
					 */
                    for (int index = 0; index < theOneChecklist.getQuestList().size(); index++) {

                        isFound = true;

                        for (int indexr = 0; indexr < userAnswers.size(); indexr++) {

                            if (userAnswers.get(indexr).getUid().equals(theOneChecklist.getQuestList().get(index).getUid())) {
                                isFound = false;
                                break;
                            }

                        }

                        /*
                            Add new user answer to users answers in the oneChecklist
                         */
                        if (isFound) {

                            theOneChecklist.getUserAnswer().add(new UserAnswer(theOneChecklist.getQuestList().get(index).getUid(), Answer.U));
                        }

                    }




                    /*
                        Finds all the previous UserAnswers that are no longer
                        in the current checklist

                        If a no longer used question is found the within the users
                        answers the 'current' field is toggled to false so is no
                        longer included within the users feedback
                     */
                    for (int indexr = 0; indexr < userAnswers.size(); indexr++) { //Loop each of users answers

                        isFound = false;

                        for (int index = 0; index < theOneChecklist.getQuestList().size(); index++) { //Loop for questions

                            if (userAnswers.get(indexr).getUid().equals(theOneChecklist.getQuestList().get(index).getUid())) {

                                isFound = true;
                                break;

                            }

                        }

                        /*
                            If the question id has not been found in the current questions
                         */
                        if (!isFound) {

                            userAnswers.get(indexr).setCurrent(false);

                        }


                    }


                }



                /*
                    Saves to theOncChecklist
                 */
                theOneChecklist.setUserAnswer(userAnswers);

            }

        } catch (IOException ex) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*
            Save to Global Check-list
         */
        globalChecklist.setTheOneChecklist(theOneChecklist);

    }

}
