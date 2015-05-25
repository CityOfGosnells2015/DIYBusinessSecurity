package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by 041402822 on 31/03/2015.
 */
public class PreferenceActivity extends ActionBarActivity implements View.OnClickListener {


    SharedPreferences prefs;
    CheckBox largeText;
    Button saveButton;
    int themeValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the value inside the intent
        themeValue = this.getIntent().getIntExtra("textValue",0);

        //Loading the correct theme application
        ChangeTheme.onActivityCreateSetTheme(this,themeValue);

        //Set the back button at ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set layout for activity
        setContentView(R.layout.activity_preference);

        //Creating preference file
        prefs = getSharedPreferences("Preferences",MODE_PRIVATE);

        //Getting the ID fields
        largeText = (CheckBox) findViewById(R.id.cbLargeText);
        saveButton = (Button) findViewById(R.id.btnSavePrefs);

        if(themeValue == 0){
            largeText.setChecked(false);
        }else{
            largeText.setChecked(true);
        }

        saveButton.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnSavePrefs){


            /*
                Text Size
             */
            int largeInt = 0;
            if (largeText.isChecked()){
                largeInt = 1;
            }
            prefs.edit().putInt("textSize", largeInt).commit();



            /*
                Other Preferences changed Here
             */


            /*
                Toast Notify of Save
             */

            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();

            /*
                Move to main
             */
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }

    }
}
