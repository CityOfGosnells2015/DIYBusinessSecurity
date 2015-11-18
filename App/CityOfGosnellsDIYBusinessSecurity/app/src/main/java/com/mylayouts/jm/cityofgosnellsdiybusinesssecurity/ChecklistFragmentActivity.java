package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;


public class ChecklistFragmentActivity  extends FragmentActivity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private ActionBar actionBar;
    private Checklist theOneChecklist;
    private TabAdapter mAdapter;
    int currentPosition;

    SharedPreferences prefs;
    int themeValue;

    /*
        Added After changes to layout
     */
    TextView txtCatagory;
    ProgressBar progressBar;
    Button homeButton;
    Button prevButton;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Loading preferences
        prefs = getSharedPreferences("Preferences", MODE_PRIVATE);
        themeValue = prefs.getInt("textSize", 0);


        setContentView(R.layout.activity_checklist_fragment);

        //get Checklist
        GlobalChecklist globalChecklist = (GlobalChecklist) getApplication();
        theOneChecklist = globalChecklist.getTheOneChecklist();

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabAdapter(getSupportFragmentManager(), theOneChecklist);

        viewPager.setAdapter(mAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

       /*
            Adding Checklist Tabs to The tab bar
        */
        for (String tab_name : theOneChecklist.listCategory()) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        /*
            Add the last tab (Save Tab)
         */
        actionBar.addTab(actionBar.newTab().setText("Save").setTabListener(this));


        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                /*
                    Updates Progess
                 */
                currentPosition = position; //updates current postions
                updateProgress();

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        /*
            Set current positon to 0
         */
        currentPosition = 0;


        /*
            First page
         */
        txtCatagory = (TextView) findViewById(R.id.txtCatagory);
        nextButton = (Button) findViewById(R.id.buttonNext);
        prevButton = (Button) findViewById(R.id.buttonPrev);

        /*
            Progress Bar
         */
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(theOneChecklist.listCategory().length + 1);

        updateProgress();

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnHome();
            }
        });

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {


    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

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
            case R.id.action_about:
                intent = new Intent(this, About_Activity.class);
                intent.putExtra("textValue",themeValue);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
        Returns to the main menu
    */
    public void returnHome(){

        /*
           Save answers to file
        */
        FileStore fileStore = new FileStore();
        try {
            fileStore.saveUserFile(theOneChecklist.getUserAnswer(), getString(R.string.file_name),this.getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);

    }



    /*
        Updates the progress and catagory
     */
    public void updateProgress(){

        /*
            Changes the Catagory from the list of cats
         */
        if(currentPosition < theOneChecklist.listCategory().length)
            txtCatagory.setText(theOneChecklist.listCategory()[viewPager.getCurrentItem()]);
        else
            txtCatagory.setText("Save");
        progressBar.setProgress(currentPosition+1);

        //Checking which page is for able or disable previous and next buttons
        if(currentPosition == 0){
            //First page - disable previous button
            prevButton.setVisibility(View.INVISIBLE);
        }else if (currentPosition == theOneChecklist.listCategory().length){
            //Last page - disable next button
            nextButton.setVisibility(View.INVISIBLE);
        }else {
            //Any other page - able both button
            prevButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }

    }




    /*
        Previous Postion
     */
    public void previousClick(View view){

        /*
            Changes the postion of the view pager by -1
         */

        if(currentPosition > 0) {

            currentPosition--;
            viewPager.setCurrentItem(currentPosition, true);
            updateProgress();

        } else {

            //When first page

        }

    }

    public void nextClick(View view){

        /*
            Changes the position of the view pages by +1
         */

        if(currentPosition < theOneChecklist.listCategory().length){
            currentPosition++;
            viewPager.setCurrentItem(currentPosition, true);
            updateProgress();

        } else{
            /// Last page

        }



    }


    public void returnHome(View view) {
    }
}