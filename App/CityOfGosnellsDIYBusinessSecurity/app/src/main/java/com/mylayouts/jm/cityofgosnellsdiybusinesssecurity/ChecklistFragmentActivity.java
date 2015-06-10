package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChecklistFragmentActivity  extends FragmentActivity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    // final Button save = (Button) viewPager.findViewById(R.id.btnSave);
    //private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    ListView listview;
    // Tab titles
    private Checklist theOneChecklist;
    private TabAdapter mAdapter;

    SharedPreferences prefs;
    int themeValue;
    boolean lastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listview = (ListView) findViewById(R.id.fraglist);
        //Loading preferences
        prefs = getSharedPreferences("Preferences", MODE_PRIVATE);
        themeValue = prefs.getInt("textSize", 0);

        //Loading the correct theme application
        //ChangeTheme.onActivityCreateSetTheme(this,themeValue);

        setContentView(R.layout.activity_checklist_fragment);

        //get Checklist
        GlobalChecklist globalChecklist = (GlobalChecklist) getApplication();
        theOneChecklist = globalChecklist.getTheOneChecklist();

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabAdapter(getSupportFragmentManager(), theOneChecklist);

        viewPager.setAdapter(mAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        // Adding Tabs
        for (String tab_name : theOneChecklist.listCategory()) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }


        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);


            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });


    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if ((tab.getPosition() > 1) && (tab.getPosition() < theOneChecklist.listCategory().length))

        {

            LinearLayout LL = new LinearLayout(this);


            //  save.setVisibility(View.VISIBLE);
            // LL.setId(R.id.btnSaveCheckList);
            //  myButton.setOnClickListener(ChecklistFragment);

            //View inflatedView = getLayoutInflater().inflate(R.layout.fragment_checklist, null);

            Button save = (Button) viewPager.findViewById(R.id.btnSave);
            save.setVisibility(View.GONE);
        }

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());

        if ((tab.getPosition() > 1) && (tab.getPosition() < theOneChecklist.listCategory().length))

        {

            LinearLayout LL = new LinearLayout(this);


            //  save.setVisibility(View.VISIBLE);
            // LL.setId(R.id.btnSaveCheckList);
            //  myButton.setOnClickListener(ChecklistFragment);


            //  save.setVisibility(View.VISIBLE);
            // LL.setId(R.id.btnSaveCheckList);
            //  myButton.setOnClickListener(ChecklistFragment);


            //View inflatedView = getLayoutInflater().inflate(R.layout.fragment_checklist, null);

            Button save = (Button) viewPager.findViewById(R.id.btnSave);
            save.setVisibility(View.GONE);
        }


        //   layout.addView(myButton);

        //   ChecklistFragment hello = new ChecklistFragment();
        //   hello.button();

    }

    public void saveAnswers(View v) {

        /*try {
            *//*
            * Compare if questions and answers is the same size
            * if true its mean all the questions was answered
            *//*
            if (ValidationAnswers()) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String currentDateTime = sdf.format(new Date());

                FileStore fileStore = new FileStore();

                //Saving user answers on file
                fileStore.saveUserFile(theOneChecklist.getUserAnswer(), currentDateTime + "_Checklist.dat", getApplicationContext());

                //Saving the name file in another file
                fileStore.saveLogFile(currentDateTime + "_Checklist", "Log_Checklist.dat", getApplicationContext());

                //Calculating score
                float totalYes = 0, totalNo = 0, totalNA = 0;

                for (UserAnswer userAnswer : theOneChecklist.getUserAnswer()) {
                    Answer answer = userAnswer.getAnswer();

                    //Checking and counting the user answer
                    if (answer.toText().equals("Yes")) {
                        totalYes++;
                    } else if (answer.toText().equals("No")) {
                        totalNo++;
                    } else if (answer.toText().equals("Not Applicable")) {
                        totalNA++;
                    }
                }

                int score = Math.round((totalYes / (theOneChecklist.getUserAnswer().size() - totalNA)) * 100);

                //     //Sending score to another activity


                //Intent/
                // Intent intent = get, FeedbackActivity.class);
                //intent.putExtra("score",score);
                //startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Please, You must answer all questions.", Toast.LENGTH_LONG).show();
                // listview = (ListView) findViewById(R.id.list);
                //listview.setSelection(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        GlobalChecklist globalChecklist = (GlobalChecklist) getApplication();
        globalChecklist.setTheOneChecklist(theOneChecklist);
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    public boolean ValidationAnswers() {
        for (UserAnswer userAnswer : theOneChecklist.getUserAnswer()) {
            Answer answer = userAnswer.getAnswer();
            if (answer.toText().equals("Unanswered")) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}