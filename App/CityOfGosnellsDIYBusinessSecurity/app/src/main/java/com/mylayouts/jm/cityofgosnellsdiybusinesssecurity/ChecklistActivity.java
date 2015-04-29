package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ChecklistActivity extends ActionBarActivity {

    Checklist theOneChecklist;
    ArrayList<UserAnswer> arrayAnswers;
    ChecklistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        GlobalChecklist globalChecklist= (GlobalChecklist) getApplication();
        theOneChecklist = globalChecklist.getTheOneChecklist();

        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new ChecklistAdapter(getApplicationContext(), R.layout.activity_display_checklist, theOneChecklist.getQuestList());
        listview.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist, menu);
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

    /*public void saveAnswers(View v) {
        float correctAns = 0;

        for(Question quest : theOneChecklist){
            if(quest.getAnwser()){
                correctAns++;
            }
        }
        int score = Math.round(correctAns/CheckList.size()*100);

        Intent intent = new Intent(ListActivity.this, ScoreActivity.class);
        intent.putExtra("Score",score);
        startActivity(intent);



    }*/
}
