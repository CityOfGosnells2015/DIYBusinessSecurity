package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


/**
 * The Save fragment displays a save button.
 *
 * When pressed the answers will be saved to file
 * The user will then be directed to the feedback page
 */
public class SaveFragment extends Fragment {


    /*
        Button
     */
    Button saveButton;
    TextView helpText;
    Checklist theOneChecklist;

    public SaveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_save, container, false);


        Toast.makeText(this.getActivity(), "Oncreate", Toast.LENGTH_SHORT).show();
        /*
            Initialise Save button
         */
        saveButton = (Button) v.findViewById(R.id.button_save);
        helpText = (TextView) v.findViewById(R.id.homeButton);

        /*
            Get The one checklist from Global vars
         */
        GlobalChecklist globalChecklist = (GlobalChecklist) getActivity().getApplication();
        theOneChecklist = globalChecklist.getTheOneChecklist();

        //Toggle if the button is enabled
        updateButtonEnable();



        /*
            If Save is Clicked
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                    /*
                        Save answers to file
                     */
                    FileStore fileStore = new FileStore();
                    try {
                        fileStore.saveUserFile(theOneChecklist.getUserAnswer(), getString(R.string.file_name), getActivity().getApplicationContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                if(allQuestionsAnswered()){

                    /*
                        Start Feedback Activity
                     */
                    Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                    startActivity(intent);

                } else {

                    Toast.makeText(getActivity(), "Please Answer All questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }


    /*
        Tests if the user has answered every question
        Enables and disables the save button
     */
    public void updateButtonEnable(){
        saveButton.setEnabled(allQuestionsAnswered());
    }


    public boolean allQuestionsAnswered(){

        ArrayList<Question> questions = theOneChecklist.getQuestList();

        //Loops every question in the array list
        for(Question q: questions){

            if(theOneChecklist.getAnswerById(q.getUid()).getAnswer() == Answer.U)
                return false;

        }

        return true;
    }


}
