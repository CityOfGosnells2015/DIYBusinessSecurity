package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChecklistFragment extends Fragment {

    Checklist theOneChecklist;
    ChecklistAdapter adapter;
    ListView listview;
    String category;
    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_checklist, container, false);

        listview = (ListView) rootView.findViewById(R.id.fraglist);
        adapter = new ChecklistAdapter(getActivity().getApplicationContext(), R.layout.activity_display_checklist, theOneChecklist.getQuestionsByCategory(category));
        listview.setAdapter(adapter);
    RelativeLayout ll = (RelativeLayout)inflater.inflate(R.layout.activity_checklist_fragment, container, false);

        return rootView;


    }


   // R.id.btnSaveCheckList.addClickListener()

    public void saveAnswers() {

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
                fileStore.saveUserFile(theOneChecklist.getUserAnswer(), currentDateTime + "_Checklist.dat", getActivity().getApplicationContext());

                //Saving the name file in another file
                fileStore.saveLogFile(currentDateTime + "_Checklist","Log_Checklist.dat", getActivity().getApplicationContext());

                //Calculating score
                float totalYes = 0, totalNo = 0, totalNA = 0;

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
                //Intent intent = new Intent(ChecklistFragment.this, FeedbackActivity.class);
                //intent.putExtra("score",score);
                //startActivity(intent);

            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Please, You must answer all questions.", Toast.LENGTH_LONG).show();
                listview = (ListView) getActivity().findViewById(R.id.list);
                listview.setSelection(0);
            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean ValidationAnswers(){
        for(UserAnswer userAnswer : theOneChecklist.getUserAnswer()) {
            Answer answer = userAnswer.getAnswer();
            if (answer.toText().equals("Unanswered")) {
                return false;
            }
        }
        return true;
    }

public void button(){

        Button myButton = new Button(getActivity());



        myButton.setText("Push Me");
     //   LinearLayout layout = (LinearLayout) ll.findViewById(R.id.btnSaveCheckList);
        //layout.addView(myButton);
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public Checklist getTheOneChecklist() {
        return theOneChecklist;
    }

    public void setTheOneChecklist(Checklist theOneChecklist) {
        this.theOneChecklist = theOneChecklist;
    }
}
