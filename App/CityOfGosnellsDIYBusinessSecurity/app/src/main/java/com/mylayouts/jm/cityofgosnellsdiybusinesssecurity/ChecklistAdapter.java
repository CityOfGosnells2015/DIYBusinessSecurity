package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class ChecklistAdapter extends ArrayAdapter<Question> {

    Context context;
    LayoutInflater vi;
    int resource;
    ArrayList<Question> listQuestion;
    UserAnswer userAnswer;
    Checklist theOneChecklist;
    boolean na = false;

    public ChecklistAdapter(Context context, int resource, ArrayList<Question> objects) {
        super(context, resource, objects);
        this.context = context;

        this.resource = resource;
        listQuestion = objects;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = null;

        GlobalChecklist globalChecklist= (GlobalChecklist) getContext();
        theOneChecklist = globalChecklist.getTheOneChecklist();

        if (view == null) {
            vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(resource, null);
        }


        TextView txtQuestion = (TextView) view.findViewById(R.id.TxtQuestion);

        final RelativeLayout oneGroup = (RelativeLayout) view.findViewById(R.id.LinearLayout1);
        final Button naButton = (Button) view.findViewById(R.id.myButton);
        final Switch selction = (Switch) view.findViewById(R.id.mySwitch);
        final ImageView sideBar = (ImageView)  view.findViewById(R.id.side_bar);

        Button btnRadio;
        Switch ansSwitch;

        //Sets the text and radio group for the holders from the information from the object.
        txtQuestion.setText(listQuestion.get(position).getQuestion());

        //Set correct option on the radio button

        //Get UserAnswer by Id Question
        userAnswer = theOneChecklist.getAnswerById(listQuestion.get(position).getUid());

        if(userAnswer.getAnswer().equals(Answer.Y)){

            selction.setChecked(true);
            selction.setEnabled(true);
            na = false;
            oneGroup.setBackgroundColor(Color.WHITE);
            naButton.setBackgroundResource((R.drawable.ic_na));
            sideBar.setBackgroundColor(Color.WHITE);

        }else if(userAnswer.getAnswer().equals(Answer.N)){

            selction.setChecked(false);
            selction.setEnabled(true);
            sideBar.setBackgroundColor(Color.WHITE);

        }else if(userAnswer.getAnswer().equals(Answer.NA)){
            btnRadio = (Button) view.findViewById(R.id.myButton);
            oneGroup.setBackgroundColor(Color.parseColor("#e5e5e5"));
            na = true;
            naButton.setBackgroundResource((R.drawable.ic_na_green));
            sideBar.setBackgroundColor(Color.parseColor("#e5e5e5"));
            selction.setEnabled(false);
        }

        else if(userAnswer.getAnswer().equals(Answer.U)){

            /*
                SET BACK TO RED
             */
            sideBar.setBackgroundColor(Color.RED);
            selction.setChecked(false);
        }



        /*
            Listener for N/A button
         */
        naButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                na = !na;

                if (na == false) {
                    naButton.setBackgroundResource((R.drawable.ic_na));
                    theOneChecklist.setAnswerByID(listQuestion.get(position).getUid(), Answer.N);
                    selction.setEnabled(true);
                    selction.setChecked(false);
                    oneGroup.setBackgroundColor(Color.WHITE);
                    sideBar.setBackgroundColor(Color.WHITE);
                }

                if (na == true) {
                    naButton.setBackgroundResource((R.drawable.ic_na_green));
                    theOneChecklist.setAnswerByID(listQuestion.get(position).getUid(),Answer.NA);
                    selction.setEnabled(false);
                    oneGroup.setBackgroundColor(Color.parseColor("#e5e5e5"));
                    sideBar.setBackgroundColor(Color.parseColor("#e5e5e5"));
                    selction.setChecked(false);
                }
            }

        });

        selction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selction.isChecked()) {
                    theOneChecklist.setAnswerByID(listQuestion.get(position).getUid(), Answer.Y);
                    na = false;
                    naButton.setBackgroundResource((R.drawable.ic_na));
                    oneGroup.setBackgroundColor(Color.WHITE);
                    sideBar.setBackgroundColor(Color.WHITE);

                }

                else if (selction.isChecked() == false){
                    theOneChecklist.setAnswerByID(listQuestion.get(position).getUid(), Answer.N);
                    sideBar.setBackgroundColor(Color.WHITE);

                }
            }



        });

        return view;
    }

}
