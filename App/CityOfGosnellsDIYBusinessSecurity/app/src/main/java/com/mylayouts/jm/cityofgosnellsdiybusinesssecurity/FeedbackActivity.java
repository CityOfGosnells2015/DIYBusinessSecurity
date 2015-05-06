package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class FeedbackActivity extends ActionBarActivity {

    TextView txtScore;
    Checklist theOneChecklist;
    ListView list;
    ArrayList logChecklist = new ArrayList();
    FileStore fileStore = new FileStore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //Set global object
        GlobalChecklist globalChecklist= (GlobalChecklist) getApplication();
        theOneChecklist = globalChecklist.getTheOneChecklist();

        //Get extras
        int score = getIntent().getIntExtra("score",0);

        txtScore = (TextView) findViewById(R.id.txtScore);
        list = (ListView) findViewById(R.id.listView);

        //Display Score
        txtScore.setText(String.valueOf(score)+"%");

        // Load previous checklist from file
        fileStore = new FileStore();
        try {
            logChecklist = fileStore.loadLogFile("Log_Checklist.dat", FeedbackActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this.getApplicationContext(), android.R.layout.simple_list_item_1, logChecklist);
        list.setAdapter(adapter);

        // ListView Item Click Listener
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) list.getItemAtPosition(position);
                updateUserAnswerObject(itemValue+".dat");

                Intent intent = new Intent(FeedbackActivity.this,ChecklistActivity.class);
                startActivity(intent);

            }
        });
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

    public void updateUserAnswerObject(String nameFile){

        try {
            theOneChecklist.setUserAnswer(fileStore.loadUserFile(nameFile,FeedbackActivity.this));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void goToMenuActivity(View v){
        Intent intent = new Intent(FeedbackActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
