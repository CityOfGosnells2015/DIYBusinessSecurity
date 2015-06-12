package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FeedbackActivity extends ActionBarActivity {

    TextView txtScore;
    Checklist theOneChecklist;
    ListView list;
    ArrayList logChecklist = new ArrayList();
    FileStore fileStore = new FileStore();
    SharedPreferences prefs;
    int themeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Loading preferences
        prefs = getSharedPreferences("Preferences",MODE_PRIVATE);
        themeValue = prefs.getInt("textSize",0);

        //Loading the correct theme application
        ChangeTheme.onActivityCreateSetTheme(this,themeValue);

        //Set the back button at ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set layout for activity
        setContentView(R.layout.activity_feedback);

        //Set global object
        GlobalChecklist globalChecklist= (GlobalChecklist) getApplication();
        theOneChecklist = globalChecklist.getTheOneChecklist();

        //Get Users last score
        int[] score = getUserScore();

        txtScore = (TextView) findViewById(R.id.txtScore);
        list = (ListView) findViewById(R.id.listView);

        //Display Score
        txtScore.setText("Your Total Score: " +  score[1] + " out of " + score[0]);

       /* // Load previous checklist from file
        fileStore = new FileStore();
        try {
            logChecklist = fileStore.loadLogFile("Log_Checklist.dat", FeedbackActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } */

        ArrayAdapter<String> adapter = new ArrayAdapter(this.getApplicationContext(), android.R.layout.simple_list_item_1, logChecklist);
        list.setAdapter(adapter);

        // ListView Item Click Listener
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) list.getItemAtPosition(position);
                updateUserAnswerObject(itemValue+".dat");

                Intent intent = new Intent(FeedbackActivity.this,ChecklistActivity.class);
                startActivity(intent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        Intent intent;
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                intent = new Intent(this, MenuActivity.class);
                intent.putExtra("textValue",themeValue);
                startActivity(intent);
                return true;

            case R.id.action_about:
                intent = new Intent(this, About_Activity.class);
                intent.putExtra("textValue",themeValue);
                startActivity(intent);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    public void updateUserAnswerObject(String nameFile){

        try {
            theOneChecklist.setUserAnswer(fileStore.loadUserFile(nameFile,FeedbackActivity.this));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void goToMenuActivity(View v){
        Intent intent = new Intent(FeedbackActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    /*
        Get a users score based on their answers for the checklist

        Score calculated in the feedback activity as user can enter the feedback
        activity and receive the score straight from their last checklist
        without having to repeat the checklist
     */

    /**
     * Returns a users score to their most recent checklist
     * (Stored in the global variable) and returns the total number
     * of used questions
     *
     *
     */
    public int[] getUserScore(){

        int[] score = {0,0};
        ArrayList<UserAnswer> answers;

        // Gets checklist from global Variables
        GlobalChecklist globalChecklist= (GlobalChecklist) getApplication();
        theOneChecklist = globalChecklist.getTheOneChecklist();

        // Gets list of users answers from the global variables
        answers =  theOneChecklist.getUserAnswer();

        /*
            Loops through each answer

            Increments total if answer is answer isCurrent && isn't not applicable
            Increments correct if user has answered yes
         */
        for(UserAnswer answer: answers){

            if(answer.isCurrent() && (answer.getAnswer() != Answer.NA)){

                //If use has answered yes
                if(answer.getAnswer() == Answer.Y) score[1]++;

                score[0]++;

            }

        }

        return score;

    }
}
