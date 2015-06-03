package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.CompoundButton.OnCheckedChangeListener;
    import android.widget.CompoundButton;
    import android.widget.ListView;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.Switch;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.io.IOException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;

public class ChecklistAdapter extends ArrayAdapter<Question> {

    Context context;
    LayoutInflater vi;
    int resource;
    ArrayList<Question> listQuestion;
    UserAnswer userAnswer;
    Checklist theOneChecklist;
    FileStore hi;
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
       // RadioGroup radioGroup  = (RadioGroup) view.findViewById(R.id.myRadioGroup);


            final Button naButton = (Button) view.findViewById(R.id.myButton);
        final Switch selction = (Switch) view.findViewById(R.id.mySwitch);
        final Button Save = (Button) view.findViewById(R.id.btnSave);
      //   Save.setVisibility(View.INVISIBLE);



        Button btnRadio;
        Switch ansSwitch;

        //Sets the text and radio group for the holders from the information from the object.
        txtQuestion.setText(listQuestion.get(position).getQuestion());

        //Set correct option on the radio button

        //Get UserAnswer by Id Question
        userAnswer = theOneChecklist.getAnswerById(listQuestion.get(position).getUid());

        if(userAnswer.getAnswer().equals(Answer.Y)){

            selction.setChecked(true);
            na = false;
            naButton.setBackgroundResource((R.drawable.ic_na));
        }else if(userAnswer.getAnswer().equals(Answer.N)){

            selction.setChecked(false);

        }else if(userAnswer.getAnswer().equals(Answer.NA)){
            btnRadio = (Button) view.findViewById(R.id.myButton);
            na = true;
            naButton.setBackgroundResource((R.drawable.ic_na_green));
        }

       /* if(userAnswer.getAnswer().equals(Answer.NA)) {

           // btnRadio = (RadioButton) view.findViewById(R.id.naButton);
            btnRadio.setChecked(true);
        }*/
        naButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                na = !na;

                if (na == false) {
                    naButton.setBackgroundResource((R.drawable.ic_na));
                    theOneChecklist.setAnswerByID(listQuestion.get(position).getUid(), Answer.N);



                }

                if (na == true) {
                    naButton.setBackgroundResource((R.drawable.ic_na_green));
                    theOneChecklist.setAnswerByID(listQuestion.get(position).getUid(),Answer.NA);

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
                }

                else if (selction.isChecked() == false){
       theOneChecklist.setAnswerByID(listQuestion.get(position).getUid(), Answer.N);

      // naButton.setBackgroundResource((R.drawable.ic_na_green));

   }
            }



        });






       /* if(userAnswer.getAnswer().equals(Answer.Y)){
            btnRadio = (RadioButton) view.findViewById(R.id.rbYes);
            btnRadio.setChecked(true);

        }else if(userAnswer.getAnswer().equals(Answer.N)){
            btnRadio = (RadioButton) view.findViewById(R.id.rbNo);
            btnRadio.setChecked(true);

        }else if(userAnswer.getAnswer().equals(Answer.NA)){
            btnRadio = (RadioButton) view.findViewById(R.id.rbNA);
            btnRadio.setChecked(true);
        }



        // RadioGroup Item Click Listener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.rbYes){
                    theOneChecklist.setAnswerByID(listQuestion.get(position).getUid(),Answer.Y);

                } else if(checkedId == R.id.rbNo){
                    theOneChecklist.setAnswerByID(listQuestion.get(position).getUid(),Answer.N);

                } else if(checkedId == R.id.rbNA){
                    theOneChecklist.setAnswerByID(listQuestion.get(position).getUid(),Answer.NA);

                }
            }
        });*/

        return view;
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
