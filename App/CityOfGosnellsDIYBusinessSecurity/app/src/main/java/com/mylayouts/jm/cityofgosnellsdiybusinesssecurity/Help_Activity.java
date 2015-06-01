package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;


public class Help_Activity extends ActionBarActivity {

    SharedPreferences prefs;
    int themeValue;
    TableLayout table;
    TableRow tr;
    HashMap emergencyList;

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
        setContentView(R.layout.activity_help_);

        populateTable();
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

    public void populateTable(){

        table = (TableLayout) findViewById(R.id.main_table);
        table.setColumnStretchable(0,true);
        table.setColumnStretchable(1,true);

        GlobalChecklist globalChecklist= (GlobalChecklist) getApplication();

        emergencyList = globalChecklist.getEmergencyContacts();

        if (!emergencyList.isEmpty()){
            for (Object key : emergencyList.keySet()){
                tr = new TableRow(this);

                //Set column name
                TextView colName = new TextView(this);
                colName.setTextColor(Color.BLACK);
                colName.setText(key.toString());
                tr.addView(colName);

                //Set column icon
                ImageView colPhone = new ImageView(this);
                colPhone.setImageResource(R.drawable.ic_call);
                final String number = emergencyList.get(key.toString()).toString();
                colPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                        startActivity(intent);
                    }
                });
                tr.addView(colPhone);
                table.addView(tr);

                //Set the dotted line
                tr = new TableRow(this);
                ImageView colLine = new ImageView(this);
                colLine.setImageResource(R.drawable.dotted_line);
                tr.addView(colLine);

                table.addView(tr);
            }
        }
    }
}
