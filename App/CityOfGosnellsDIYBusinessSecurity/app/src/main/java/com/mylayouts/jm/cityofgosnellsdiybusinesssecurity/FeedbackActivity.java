package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class FeedbackActivity extends ActionBarActivity {

    TextView txtPercent;
    Checklist theOneChecklist;
    SharedPreferences prefs;
    int themeValue;
    ImageView lockImage;
    double percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Loading preferences
        prefs = getSharedPreferences("Preferences",MODE_PRIVATE);
        themeValue = prefs.getInt("textSize",0);

        //Loading the correct theme application
        ChangeTheme.onActivityCreateSetTheme(this, themeValue);

        //Set layout for activity
        setContentView(R.layout.activity_feedback);

        lockImage = (ImageView) findViewById(R.id.lock_image);
        txtPercent = (TextView) findViewById(R.id.percentage);

        //Get Users last score
        //Score[1] is the number of answers user has answered as Y
        //Score is the total number of questions user has answered
        int[] score = getUserScore();

        /*
            Set Lock closed image is users score is = 100
            Set to unlocked otherwise

            Set the Percentage in the image
         */
        percentage = (double)(score[1])/(double)(score[0]);

        if (percentage == 1){
            //Set to locked image
            lockImage.setImageResource(R.drawable.lock_closed);

        }else{

            lockImage.setImageResource(R.drawable.lock_open);
        }

        // Sets the text
        txtPercent.setText(String.format("%2.0f%%",percentage * 100));
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

            if(answer.isCurrent() && answer.getAnswer() != Answer.NA && answer.getAnswer() != Answer.U){

                //If use has answered yes
                if(answer.getAnswer() == Answer.Y) score[1]++;
                score[0]++;

            }

        }

        return score;

    }
}
