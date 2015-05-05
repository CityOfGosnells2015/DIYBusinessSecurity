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
    ArrayList<UserAnswer> listUserAnswers;

    public ChecklistAdapter(Context context, int resource, ArrayList<Question> objects, ArrayList<UserAnswer> objAnswers) {
        super(context, resource, objects);
        this.context = context;

        this.resource = resource;
        listQuestion = objects;
        listUserAnswers = objAnswers;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

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
        if(listUserAnswers.get(position).getAnswer().equals(Answer.Y)){
            btnRadio = (RadioButton) view.findViewById(R.id.rbYes);
            btnRadio.setChecked(true);

        }else if(listUserAnswers.get(position).getAnswer().equals(Answer.N)){
            btnRadio = (RadioButton) view.findViewById(R.id.rbNo);
            btnRadio.setChecked(true);

        }else if(listUserAnswers.get(position).getAnswer().equals(Answer.NA)){
            btnRadio = (RadioButton) view.findViewById(R.id.rbNA);
            btnRadio.setChecked(true);
        }

        // RadioGroup Item Click Listener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.rbYes){
                    listUserAnswers.get(position).setAnswer(Answer.Y);

                } else if(checkedId == R.id.rbNo){
                    listUserAnswers.get(position).setAnswer(Answer.N);

                } else if(checkedId == R.id.rbNA){
                    listUserAnswers.get(position).setAnswer(Answer.NA);

                }
            }
        });

        return view;
    }
}
