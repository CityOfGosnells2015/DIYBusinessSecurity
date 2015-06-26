package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MenuActivity extends ActionBarActivity implements View.OnClickListener{

    SharedPreferences prefs;
    int themeValue;
    Button preferenceButton, helpButton, notificationButton, linkButton, checklistButton, myNumberButton;

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

        notificationButton = (Button) findViewById(R.id.btnNotification);
        notificationButton.setOnClickListener(this);

        linkButton = (Button) findViewById(R.id.btnLink);
        linkButton.setOnClickListener(this);

        checklistButton = (Button) findViewById(R.id.btnChecklist);
        checklistButton.setOnClickListener(this);

        myNumberButton = (Button) findViewById(R.id.btnImportantNumbers);
        myNumberButton.setOnClickListener(this);

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

            case R.id.action_about:
                intent = new Intent(this, About_Activity.class);
                intent.putExtra("textValue",themeValue);
                startActivity(intent);
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

        //Move to Help Activity
        else if (v.getId() == R.id.btnHelp){
            Intent intent = new Intent(this, Help_Activity.class);
            intent.putExtra("textValue",themeValue);
            startActivity(intent);
        }

        //Move to MyLink Activity
        else if (v.getId() == R.id.btnLink) {
            Intent intent = new Intent(this, LinksActivity.class);
            intent.putExtra("textValue",themeValue);
            startActivity(intent);
        }

        //Move to Checklist Activity
        else if (v.getId() == R.id.btnChecklist){
            Intent intent = new Intent(this, ChecklistFragmentActivity.class);
            intent.putExtra("textValue",themeValue);
            startActivity(intent);
        }

        //Move to My Numbers Activity
        else if (v.getId() == R.id.btnImportantNumbers){
            Intent intent = new Intent(this, MyNumbersActivity.class);
            intent.putExtra("textValue",themeValue);
            startActivity(intent);
        }
    }
}
