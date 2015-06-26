package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Gustavo on 8/06/2015.
 */
public class EditNumberActivity extends ActionBarActivity {

    private int elemPosition = -1;
    private FileManager fm = new FileManager();
    private ArrayList<Link> numbersList = new ArrayList();
    private EditText txtName, txtWebPage, txtPhone;
    SharedPreferences prefs;
    int themeValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Loading preferences
        prefs = getSharedPreferences("Preferences",MODE_PRIVATE);
        themeValue = prefs.getInt("textSize",0);

        //Loading the correct theme application
        ChangeTheme.onActivityCreateSetTheme(this,themeValue);

        //Set layout for activity
        setContentView(R.layout.activity_edit_number);

        txtName = (EditText)findViewById(R.id.txtName);
        txtPhone = (EditText)findViewById(R.id.txtPhone);
        txtWebPage = (EditText)findViewById(R.id.txtWebPage);

        elemPosition = getIntent().getIntExtra("elemPosition",-1);

        if(elemPosition!= -1){
            numbersList = fm.readFile(this, "MyNumbers.txt");

            txtName.setText(numbersList.get(elemPosition).getName());
            txtPhone.setText(numbersList.get(elemPosition).getPhone());
            txtWebPage.setText(numbersList.get(elemPosition).getWebPage());


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        Intent intent;
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                intent = new Intent(this, MyNumbersActivity.class);
                intent.putExtra("textValue",themeValue);
                startActivity(intent);
                return true;

            case R.id.action_about:
                intent = new Intent(this, About_Activity.class);
                intent.putExtra("textValue",themeValue);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void saveChanges(View v){

        //Updating arrayList
        numbersList.get(elemPosition).setName(txtName.getText().toString());
        numbersList.get(elemPosition).setPhone(txtPhone.getText().toString());
        numbersList.get(elemPosition).setWebPage(txtWebPage.getText().toString());

        if(fm.deleteFile(this)){//Delete the file
            //Create a new file and add each element again
            for(Link object : numbersList){
                fm.writeOnFile(object,this,"MyNumbers.txt");
            }
            Intent intent = new Intent(this, MyNumbersActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "Sorry, Try again!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteNumber(View v){

        //Updating arrayList
        numbersList.remove(elemPosition);

        if(fm.deleteFile(this)){//Delete the file
            //Create a new file and add each element again
            for(Link object : numbersList){
                fm.writeOnFile(object,this,"MyNumbers.txt");
            }
            Intent intent = new Intent(this, MyNumbersActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "Sorry, Try again!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void showMenu(View v){
        Intent intent;
        intent = new Intent(this, MyNumbersActivity.class);
        intent.putExtra("textValue",themeValue);
        startActivity(intent);
    }
}
