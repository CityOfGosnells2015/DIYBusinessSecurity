package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.ImageView;
    import android.widget.TextView;

    import java.util.ArrayList;

public class ChecklistAdapter extends ArrayAdapter<Question> {
    private final Context context;

    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    ArrayList<Question> objQuestion;
    ArrayList<UserAnswer> objUserAnswers;





    public ChecklistAdapter(Context context, int resource, ArrayList<Question> objects) {
        super(context, resource, objects);
        this.context = context;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        objQuestion = objects;
        //objUserAnswers = objAnswers;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;

        //Creates holders for the views that are used on the activity
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
            holder.Question = (TextView) v.findViewById(R.id.TxtQuestion);
            holder.Category = (TextView) v.findViewById(R.id.TxtCategory);
            holder.Anwser = (CheckBox) v.findViewById(R.id.CboxAnwser);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.imageview.setImageResource(R.drawable.ic_launcher);

            /*
                Populate Question
             */
        //Sets the text and checkbox for the holders from the information from the object.
        holder.Question.setText(objQuestion.get(position).getQuestion());
        holder.Category.setText(objQuestion.get(position).getCategory());


        /*holder.Anwser.setChecked(objUserAnswers.getAnswer());
        holder.Anwser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                objQuestion.get(position).setanwser(true);
            }

        });*/
        return v;
    }




    static class ViewHolder {
        public ImageView imageview;
        public TextView Question;
        public TextView Category;
        public CheckBox Anwser;


    }




}
