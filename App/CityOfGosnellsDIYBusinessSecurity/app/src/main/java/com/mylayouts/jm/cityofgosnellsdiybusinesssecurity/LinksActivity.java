package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


public class LinksActivity extends ActionBarActivity {


    ArrayList<Link> linksList = new ArrayList<Link>();
    FileManager fileManager = new FileManager();
    TableLayout table;
    TableRow tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);
        linksList = fileManager.readFile(LinksActivity.this);
        populateTable();
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

    public void addMyLinks(View v){
        Intent intent = new Intent(LinksActivity.this, AddLinkActivity.class);
        startActivity(intent);
    }



    public void populateTable(){

        table = (TableLayout) findViewById(R.id.main_table);
        table.setColumnStretchable(0,true);
        table.setColumnStretchable(1,true);
        table.setColumnStretchable(2,true);
        table.setColumnStretchable(3,true);
        tr = new TableRow(this);
        tr.setBackgroundColor(Color.GRAY);

        //Set head line on the table
        TextView txtName = new TextView(this);
        txtName.setText("Name"); // set the text for the header
        txtName.setTextColor(Color.WHITE); // set the color
        txtName.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr.addView(txtName); // add the column to the table row here

        TextView txtPhone = new TextView(this);
        txtPhone.setText("Phone"); // set the text for the header
        txtPhone.setTextColor(Color.WHITE); // set the color
        txtPhone.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr.addView(txtPhone); // add the column to the table row here

        TextView txtWebSite = new TextView(this);
        txtWebSite.setText("WebSite"); // set the text for the header
        txtWebSite.setTextColor(Color.WHITE); // set the color
        txtWebSite.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr.addView(txtWebSite); // add the column to the table row here

        TextView txtEdit = new TextView(this);
        txtEdit.setText("Edit"); // set the text for the header
        txtEdit.setTextColor(Color.WHITE); // set the color
        txtEdit.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr.addView(txtEdit); // add the column to the table row here

        table.addView(tr);

        if (!linksList.isEmpty()){
            for(int num=0; num < linksList.size(); num++) {
                TableRow row = new TableRow(this);
                if (num % 2 != 0) {
                    row.setBackgroundColor(Color.GRAY);
                }
                //Set column name
                TextView colName = new TextView(this);
                colName.setText(linksList.get(num).getName());
                row.addView(colName);

                //Set column phone
                ImageView colPhone = new ImageView(this);
                colPhone.setImageResource(R.drawable.ic_call);
                final String number = linksList.get(num).getPhone();
                colPhone.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ number));
                        startActivity(intent);
                    }
                });
                row.addView(colPhone);

                //Set column website
                ImageView colWebSite = new ImageView(this);
                colWebSite.setImageResource(R.drawable.ic_website);
                final String website = linksList.get(num).getWebPage();
                colWebSite.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + website));
                        startActivity(intent);
                    }
                });
                row.addView(colWebSite);

                //Set column edit
                ImageView colEdit = new ImageView(this);
                colEdit.setImageResource(R.drawable.ic_edit);
                final int position = num;
                colEdit.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(LinksActivity.this, EditLinkActivity.class);
                        intent.putExtra("elemPosition",position);
                        startActivity(intent);
                    }
                });
                row.addView(colEdit);

                table.addView(row);
            }
        }
    }

}
