package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {

    Checklist theOneChecklist;

    public TabAdapter(FragmentManager fm,Checklist theOneChecklist) {

        super(fm);
        this.theOneChecklist = theOneChecklist;

    }


    @Override
    public Fragment getItem(int index){

        ChecklistFragment checkFrag = new ChecklistFragment();
        checkFrag.setCategory(theOneChecklist.listCategory()[index]);
        checkFrag.setTheOneChecklist(theOneChecklist);
        return checkFrag;

    }

    @Override
    public int getCount() {

        // get item count - equal to number of tabs
        return theOneChecklist.listCategory().length;

    }

}
