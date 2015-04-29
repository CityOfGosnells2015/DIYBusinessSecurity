package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.CheckBox;
    import android.widget.ImageView;
    import android.widget.RadioGroup;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.util.ArrayList;

public class ChecklistAdapter extends ArrayAdapter<Question> {
    private final Context context;

    LayoutInflater vi;
    int resource;
    ViewHolder holder;
    ArrayList<Question> objQuestion;
    ArrayList<UserAnswer> objUserAnswers;





    public ChecklistAdapter(Context context, int resource, ArrayList<Question> objects) {
        super(context, resource, objects);
        this.context = context;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        objQuestion = objects;
        //objUserAnswers = objAnswers;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design
        View view = convertView;

        //Creates holders for the views that are used on the activity
        if (view == null) {
            holder = new ViewHolder();
            view = vi.inflate(resource, null);
            //holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
            holder.txtQuestion = (TextView) view.findViewById(R.id.TxtQuestion);
            holder.txtCategory = (TextView) view.findViewById(R.id.TxtCategory);
            holder.radioGroup  = (RadioGroup) view.findViewById(R.id.myRadioGroup);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //holder.imageview.setImageResource(R.drawable.ic_launcher);

            /*
                Populate Question
             */
        //Sets the text and checkbox for the holders from the information from the object.
        holder.txtQuestion.setText(objQuestion.get(position).getQuestion());
        holder.txtCategory.setText(objQuestion.get(position).getCategory());
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.rbYes){
                    Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();

                } else if(checkedId == R.id.rbNo){
                    Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();

                } else if(checkedId == R.id.rbNA){
                    Toast.makeText(context, "N/A", Toast.LENGTH_SHORT).show();

                }
            }
        });




        /*holder.Anwser.setChecked(objUserAnswers.getAnswer());
        holder.Anwser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                objQuestion.get(position).setanwser(true);
            }

        });*/
        return view;
    }

    static class ViewHolder {
        //public ImageView imageview;
        public TextView txtQuestion;
        public TextView txtCategory;
        public RadioGroup radioGroup ;


    }




}
