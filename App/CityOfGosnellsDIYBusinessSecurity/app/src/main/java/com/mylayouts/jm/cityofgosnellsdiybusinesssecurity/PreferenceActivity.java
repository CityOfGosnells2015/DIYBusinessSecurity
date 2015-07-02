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
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by 041402822 on 31/03/2015.
 */
public class PreferenceActivity extends ActionBarActivity {


    SharedPreferences prefs;
    Switch textSizeSwitch;
    int themeValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the value inside the intent
        themeValue = this.getIntent().getIntExtra("textValue",0);

        //Loading the correct theme application
        ChangeTheme.onActivityCreateSetTheme(this,themeValue);

        //Set layout for activity
        setContentView(R.layout.activity_preference);

        //Creating preference file
        prefs = getSharedPreferences("Preferences",MODE_PRIVATE);

        //Getting the ID fields
        textSizeSwitch = (Switch) findViewById(R.id.switchChangeSize);

        if(themeValue == 0){
            textSizeSwitch.setChecked(false);
        }else{
            textSizeSwitch.setChecked(true);
        }

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

    //Set preference for text size
    public void changeTextSize(View v){
        int largeInt = 0;

        if(textSizeSwitch.isChecked()){
            largeInt=1;
        }
        //Save preference on the file
        prefs.edit().putInt("textSize", largeInt).commit();


    }

    public void showLetter(View v){
        Intent intent = new Intent(this, MayorLetter.class);
        intent.putExtra("textValue",themeValue);
        startActivity(intent);
    }

    public void showBomb(View v){
        Intent intent = new Intent(this, BombThreatActivity.class);
        intent.putExtra("textValue",themeValue);
        startActivity(intent);
    }

    public void showMenu(View v){
        Intent intent;
        intent = new Intent(this, MenuActivity.class);
        intent.putExtra("textValue",themeValue);
        startActivity(intent);
    }
}
