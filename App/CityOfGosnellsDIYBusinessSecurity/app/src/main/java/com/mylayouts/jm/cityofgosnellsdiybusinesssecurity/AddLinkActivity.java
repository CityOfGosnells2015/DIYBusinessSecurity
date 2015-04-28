package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link);

        txtName = (EditText)findViewById(R.id.txtName);
        txtPhone = (EditText)findViewById(R.id.txtPhone);
        txtWebPage = (EditText)findViewById(R.id.txtWebPage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_links, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void cancel(View v){
        Intent intent = new Intent(AddLinkActivity.this, LinksActivity.class);
        startActivity(intent);
    }

    public void saveMyLink(View v){
        myLink.setName(txtName.getText().toString());
        myLink.setPhone(txtPhone.getText().toString());
        myLink.setWebPage(txtWebPage.getText().toString());

        if(fileManager.writeOnFile(myLink,AddLinkActivity.this)){
            Toast.makeText(getBaseContext(), "Saved...",
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddLinkActivity.this, LinksActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "Sorry, Try again!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
