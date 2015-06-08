package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class EditLinkActivity extends ActionBarActivity {

    private int elemPosition = -1;
    private FileManager fm = new FileManager();
    private ArrayList<Link> linksList = new ArrayList();
    private Link link = new Link();
    private EditText txtName;
    private EditText txtPhone;
    private EditText txtWebPage;
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

        //Set the back button at ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set layout for activity
        setContentView(R.layout.activity_edit_link);

        txtName = (EditText)findViewById(R.id.txtName);
        txtPhone = (EditText)findViewById(R.id.txtPhone);
        txtWebPage = (EditText)findViewById(R.id.txtWebPage);

        elemPosition = getIntent().getIntExtra("elemPosition",-1);

        if(elemPosition!= -1){
            linksList = fm.readFile(EditLinkActivity.this, "MyImportantLinks.txt");

            txtName.setText(linksList.get(elemPosition).getName());
            txtPhone.setText(linksList.get(elemPosition).getPhone());
            txtWebPage.setText(linksList.get(elemPosition).getWebPage());

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
                intent = new Intent(this, LinksActivity.class);
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
        linksList.get(elemPosition).setName(txtName.getText().toString());
        linksList.get(elemPosition).setPhone(txtPhone.getText().toString());
        linksList.get(elemPosition).setWebPage(txtWebPage.getText().toString());

        if(fm.deleteFile(EditLinkActivity.this)){//Delete the file
            //Create a new file and add each element again
            for(Link object : linksList){
                fm.writeOnFile(object,EditLinkActivity.this,"MyImportantLinks.txt");
            }
            Intent intent = new Intent(EditLinkActivity.this, LinksActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "Sorry, Try again!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteLink(View v){

        //Updating arrayList
        linksList.remove(elemPosition);

        if(fm.deleteFile(EditLinkActivity.this)){//Delete the file
            //Create a new file and add each element again
            for(Link object : linksList){
                fm.writeOnFile(object,EditLinkActivity.this,"MyImportantLinks.txt");
            }
            Intent intent = new Intent(EditLinkActivity.this, LinksActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "Sorry, Try again!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
