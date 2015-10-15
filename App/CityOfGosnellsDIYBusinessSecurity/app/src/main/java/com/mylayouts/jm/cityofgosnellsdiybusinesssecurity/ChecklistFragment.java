package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

/**
 *  Checklist Fragment
 *
 *  Orgainses quetsions by catagory
 *  Each Catagory is on a Fragment
 */
public class ChecklistFragment extends Fragment {

    Checklist theOneChecklist;
    ChecklistAdapter adapter;
    ListView listview;
    String category;


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_checklist, container, false);


        /*
            Creates the Adapter in the fragment
         */
        listview = (ListView) rootView.findViewById(R.id.fraglist);
        adapter = new ChecklistAdapter(getActivity().getApplicationContext(), R.layout.activity_display_checklist, theOneChecklist.getQuestionsByCategory(category));
        listview.setAdapter(adapter);



        return rootView;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }


    public void setTheOneChecklist(Checklist theOneChecklist) {
        this.theOneChecklist = theOneChecklist;
    }


}
