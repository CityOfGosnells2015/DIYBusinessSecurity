package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;


public class MyNumbersActivity extends ActionBarActivity {

    ArrayList<Link> numbersList = new ArrayList();
    FileManager fileManager = new FileManager();
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

        //Set layout for activity
        setContentView(R.layout.activity_my_numbers);

        numbersList = fileManager.readFile(this, "MyNumbers.txt");

        ListView listview = (ListView) findViewById(R.id.list1);
        NumberAdapter adapter = new NumberAdapter(getApplicationContext(), numbersList);
        listview.setAdapter(adapter);
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

    public void addMyNumber(View v){
        Intent intent = new Intent(this, AddNumberActivity.class);
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
