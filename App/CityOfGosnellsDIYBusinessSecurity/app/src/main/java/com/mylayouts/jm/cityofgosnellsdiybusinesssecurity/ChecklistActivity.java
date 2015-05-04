package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChecklistActivity extends ActionBarActivity {

    Checklist theOneChecklist;
    ChecklistAdapter adapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        GlobalChecklist globalChecklist= (GlobalChecklist) getApplication();
        theOneChecklist = globalChecklist.getTheOneChecklist();

        /*Deletar antes de commit*/
        for(int x = 0; x < 43; x++) {

                theOneChecklist.getQuestList().remove(46-x);
                theOneChecklist.getUserAnswer().remove(46-x);
        }
        //Magica do padeta!!!

        listview = (ListView)findViewById(R.id.list);
        adapter = new ChecklistAdapter(getApplicationContext(), R.layout.activity_display_checklist, theOneChecklist.getQuestList(), theOneChecklist.getUserAnswer());
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

    public void saveAnswers(View v) {

        try{
            /*
            * Compare if questions and answers is the same size
            * if true its mean all the questions was answered
            */
            if(ValidationAnswers()) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String currentDateTime = sdf.format(new Date());

                FileStore fileStore = new FileStore();

                //Saving user answers on file
                fileStore.saveUserFile(theOneChecklist.getUserAnswer(), currentDateTime + "_Checklist.dat", this.getApplicationContext());

                //Saving the name file in another file
                fileStore.saveLogFile(currentDateTime + "_Checklist","Log_Checklist.dat", this.getApplicationContext());

                //Calculating score
                int totalYes = 0, totalNo = 0, totalNA = 0;

                for(UserAnswer userAnswer : theOneChecklist.getUserAnswer()){
                    Answer answer = userAnswer.getAnswer();

                    //Checking and counting the user answer
                    if(answer.toText().equals("Yes")){
                        totalYes++;
                    } else if(answer.toText().equals("No")){
                        totalNo++;
                    } else if(answer.toText().equals("Not Applicable")){
                        totalNA++;
                    }
                }

                int score = Math.round((totalYes / (theOneChecklist.getUserAnswer().size() - totalNA)) * 100);

                //Sending score to another activity
                Intent intent = new Intent(ChecklistActivity.this, FeedbackActivity.class);
                intent.putExtra("score",score);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(),"Please, You must answer all questions.",Toast.LENGTH_LONG).show();
                listview = (ListView)findViewById(R.id.list);
                listview.setSelection(0);
            }

        } catch(IOException e){
             e.printStackTrace();
        }
    }

    public boolean ValidationAnswers(){
        boolean isValid = true;
        for(UserAnswer userAnswer : theOneChecklist.getUserAnswer()) {
            Answer answer = userAnswer.getAnswer();
            if (answer.toText().equals("Unanswered")) {
                isValid = false;
            }
        }
        return isValid;
    }
}
