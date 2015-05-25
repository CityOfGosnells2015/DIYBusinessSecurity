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
 *
 * James McNeil 30/03/2015
 */
public class WelcomeActivity extends Activity implements View.OnClickListener{

    Button firstTimeClick;
    SharedPreferences prefs;
    ArrayList<Answer> userAnswers;
    Checklist theOneChecklist;
    /*
    URL Of Checklist Data to be retrieved.
    */
    private final static String CHECKLIST_URL = "http://www.gosnells.wa.gov.au/feed.rss?listname=Security%20Audit%20Checklist";
    private final static String FILE_NAME = "answers.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Create Checklist
        theOneChecklist = new Checklist();

        prefs =getSharedPreferences("AppInfo",MODE_PRIVATE);
        prefs.edit().putBoolean("VersionChanged",false).commit();

        //Initialise Button and add listener
        firstTimeClick = (Button) findViewById(R.id.btnFirstClick);
        firstTimeClick.setOnClickListener(this);
        firstTimeClick.setEnabled(false);

        if (isOnline()) {
            DownloadJSONTask task = new DownloadJSONTask();
            task.execute(new String[]{CHECKLIST_URL});
        }else{

            /*
                Promt user to connect to network or exit application
             */
            noNetworkErrorDialog();
            Log.d("Not Online","NOT CONNECTED TO THE INTERNET");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }


    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onRestart(){
        super.onRestart();

        if (isOnline()) {
            DownloadJSONTask task = new DownloadJSONTask();
            task.execute(new String[]{CHECKLIST_URL});
        }else{

            /*
                Promt user to connect to network or exit application
             */
            noNetworkErrorDialog();
            Log.d("Not Online","NOT CONNECTED TO THE INTERNET");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnFirstClick){

            //If app is being used for first time]
            if (prefs.getBoolean("firstrun",true)){

                //First Time Run Code Goes Here
                prefs.edit().putBoolean("firstrun",false).commit(); // Sets firstrun to false


                //Go to Letter
                Intent intent = new Intent(this, MayorLetter.class);
                startActivity(intent);


            }
            else {

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
         *
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

                /*
                    Splash Screen will always be visible for at least 3 seconds
                    sleep( time in milliseconds)
                 */
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e("Interrupted", "" + e.getMessage());
            } catch (JSONException e) {
                Log.e("JSON Exception",e.getMessage());
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
     *
     * Returns Array Of "Unanswered" answers
     *
     * @return
     * @author James McNeil
     */
    private ArrayList<UserAnswer> createNewAnswerList(){

        ArrayList<UserAnswer> userAnswers = new ArrayList<UserAnswer>();
        UserAnswer addedAnswer;

        for(int index=0;index<theOneChecklist.getQuestList().size();index++){

            addedAnswer = new UserAnswer(theOneChecklist.getQuestionByIndex(index).getUid(),Answer.U);
            userAnswers.add(addedAnswer);

        }

        return userAnswers;

    }

    /**
     * Checks for an internet Connection
     *
     * (From Tutorials)
     *
     * @return Boolean
     */
    protected boolean isOnline(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else return false;

    }

    /**
     *
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


    private void createChecklist(){

        /*
            Load Users Answers from file
         */
        //Initialise File
        FileStore fileStore = new FileStore();

        //Create File
        File answerFile = new File(getApplicationContext().getFilesDir().getPath().toString() + "/" +FILE_NAME);
        try{

            ArrayList<UserAnswer> userAnswers;

            //Check if Exists
            if(!answerFile.exists()){
                //Create list, save to file
                userAnswers = createNewAnswerList();
                theOneChecklist.setUserAnswer(userAnswers);
                fileStore.saveUserFile(userAnswers,FILE_NAME,this.getApplicationContext());

            }else{
                //Load User file
                userAnswers = fileStore.loadUserFile(FILE_NAME,this.getApplicationContext());
                theOneChecklist.setUserAnswer(userAnswers);

            }

        } catch(IOException ex){

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*
            Store Checklists version number in shared preferences

            VersionChanged will toggle to true if a checklist has changed since last downloaded
         */
        if(prefs.getInt("VersionNumber",0) != theOneChecklist.getVersionNumber()){

            prefs.edit().putBoolean("VersionChanged",true).commit();

        }
        prefs.edit().putInt("VersionNumber",theOneChecklist.getVersionNumber()).commit();


        /*
            Save to Global Checklist
         */
        GlobalChecklist globalChecklist= (GlobalChecklist) getApplication();
        globalChecklist.setTheOneChecklist(theOneChecklist);

    }

}
