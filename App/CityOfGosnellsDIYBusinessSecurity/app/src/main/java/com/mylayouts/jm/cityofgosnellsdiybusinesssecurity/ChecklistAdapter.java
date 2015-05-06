package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.TextView;

    import java.util.ArrayList;

public class ChecklistAdapter extends ArrayAdapter<Question> {

    Context context;
    LayoutInflater vi;
    int resource;
    ArrayList<Question> listQuestion;
    UserAnswer userAnswer;
    Checklist theOneChecklist;

    public ChecklistAdapter(Context context, int resource, ArrayList<Question> objects) {
        super(context, resource, objects);
        this.context = context;

        this.resource = resource;
        listQuestion = objects;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        GlobalChecklist globalChecklist= (GlobalChecklist) getContext();
        theOneChecklist = globalChecklist.getTheOneChecklist();

        if (view == null) {
            vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(resource, null);
        }

        TextView txtQuestion = (TextView) view.findViewById(R.id.TxtQuestion);
        RadioGroup radioGroup  = (RadioGroup) view.findViewById(R.id.myRadioGroup);
        RadioButton btnRadio;

        //Sets the text and radio group for the holders from the information from the object.
        txtQuestion.setText(listQuestion.get(position).getQuestion());

        //Set correct option on the radio button

        //Get UserAnswer by Id Question
        userAnswer = theOneChecklist.getAnswerById(listQuestion.get(position).getUid());

        if(userAnswer.getAnswer().equals(Answer.Y)){
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
        });

        return view;
    }
}
