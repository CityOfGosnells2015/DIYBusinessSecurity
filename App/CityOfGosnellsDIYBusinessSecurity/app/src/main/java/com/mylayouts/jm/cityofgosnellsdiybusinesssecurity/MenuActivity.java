package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MenuActivity extends ActionBarActivity implements View.OnClickListener{

    SharedPreferences prefs;
    int themeValue;
    Button preferenceButton, helpButton, aboutButton, notificationButton, checklistButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Loading preferences
        prefs = getSharedPreferences("Preferences",MODE_PRIVATE);
        themeValue = prefs.getInt("textSize",0);

        //Loading the correct theme application
        ChangeTheme.onActivityCreateSetTheme(this,themeValue);

        //Set layout for activity
        setContentView(R.layout.activity_menu);

        preferenceButton = (Button) findViewById(R.id.btnPreference);
        preferenceButton.setOnClickListener(this);

        helpButton = (Button) findViewById(R.id.btnHelp);
        helpButton.setOnClickListener(this);

        aboutButton = (Button) findViewById(R.id.btnAbout);
        aboutButton.setOnClickListener(this);

        notificationButton = (Button) findViewById(R.id.btnNotification);
        notificationButton.setOnClickListener(this);

        checklistButton = (Button) findViewById(R.id.btnChecklist);
        checklistButton.setOnClickListener(this);

        /*
            THIS IS TEST CODE --- PLS DELETE IF STILL HERE
         */

        GlobalChecklist globalChecklist = (GlobalChecklist)getApplication();
        Checklist theOneChecklist = globalChecklist.getTheOneChecklist();



        for(Question q:theOneChecklist.getQuestList()){

            Log.d("Global Quest TEST","\nQuestion: " + q.getQuestion() + "\nCatagory: " + q.getCategory());

        }

        Log.d("TEST","" + theOneChecklist.getUserAnswer().size());

        for(UserAnswer a:theOneChecklist.getUserAnswer()){

            Log.d("Global Answer TEST","\nAnswer: " + a.getAnswer().toText());

        }

        /*
           ----------------------------------------------
         */

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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        //Move to Preference Activity
        if (v.getId() == R.id.btnPreference){
            Intent intent = new Intent(this, PreferenceActivity.class);
            intent.putExtra("textValue",themeValue);
            startActivity(intent);
        }

        //Move to Notification Activity
        else if (v.getId() == R.id.btnNotification){
            Intent intent = new Intent(this, NotificationActivity.class);
            intent.putExtra("textValue",themeValue);
            startActivity(intent);
        }

        //Move to About Activity
        else if (v.getId() == R.id.btnAbout){
            Intent intent = new Intent(this, About_Activity.class);
            intent.putExtra("textValue",themeValue);
            startActivity(intent);
        }

        //Move to Help Activity
        else if (v.getId() == R.id.btnHelp){
            Intent intent = new Intent(this, Help_Activity.class);
            intent.putExtra("textValue",themeValue);
            startActivity(intent);
        }

        //Move to Checklist Activity
        else if (v.getId() == R.id.btnChecklist){
            Intent intent = new Intent(this, ChecklistActivity.class);
            intent.putExtra("textValue",themeValue);
            startActivity(intent);
        }
    }
}
