package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;


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

    Checklist theOneChecklist;

    public SaveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_save, container, false);

        /*
            Initialise Save button
         */
        saveButton = (Button) v.findViewById(R.id.button_save);

        /*
            Get The one checklist from Global vars
         */
        GlobalChecklist globalChecklist = (GlobalChecklist) getActivity().getApplication();
        theOneChecklist = globalChecklist.getTheOneChecklist();

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

                    /*
                        Start Feedback Activity
                     */
                    Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                    startActivity(intent);

            }
        });

        return v;
    }


}
