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


public class AddLinkActivity extends ActionBarActivity {

    private FileManager fileManager = new FileManager();
    private Link myLink = new Link();
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
        setContentView(R.layout.activity_add_link);

        txtName = (EditText)findViewById(R.id.txtName);
        txtPhone = (EditText)findViewById(R.id.txtPhone);
        txtWebPage = (EditText)findViewById(R.id.txtWebPage);
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

    public void saveMyLink(View v){
        myLink.setName(txtName.getText().toString());
        myLink.setPhone(txtPhone.getText().toString());
        myLink.setWebPage(txtWebPage.getText().toString());

        if(fileManager.writeOnFile(myLink,AddLinkActivity.this,"MyImportantLinks.txt")){
            Intent intent = new Intent(AddLinkActivity.this, LinksActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "Sorry, Try again!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
