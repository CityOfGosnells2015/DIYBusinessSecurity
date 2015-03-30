package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


/**
 * Welcome Screen
 *
 * Contains 1 Button, If is it users first time using the app
 * The button will navigate user to Letter
 *
 * else will user to main menu
 *
 * James McNeil 30/03/2015
 */
public class WelcomeActivity extends ActionBarActivity implements View.OnClickListener{



    Button firstTimeClick;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        prefs =getSharedPreferences("FirstTimePreferences",MODE_PRIVATE);

        //Initialise Button and add listener
        firstTimeClick = (Button) findViewById(R.id.btnFirstClick);
        firstTimeClick.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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
}
