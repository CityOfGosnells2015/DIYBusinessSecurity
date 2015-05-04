package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


public class FeedbackActivity extends ActionBarActivity {

    TextView txtScore;
    ListView list;
    ArrayList<String> logChecklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //Get extras
        int score = getIntent().getIntExtra("score", 0);

        txtScore = (TextView) findViewById(R.id.txtScore);
        list = (ListView) findViewById(R.id.listView);

        txtScore.setText(String.valueOf(score));


        /*FileStore fileStore = new FileStore();
        try {
            logChecklist = fileStore.loadLogFile("Log_Checklist.dat", FeedbackActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        logChecklist.add("Log 1");
        logChecklist.add("Log 2");
        logChecklist.add("Log 3");
        logChecklist.add("Log 4");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getApplicationContext(), android.R.layout.simple_list_item_1, logChecklist);
        list.setAdapter(adapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
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
}
