package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabAdapter extends FragmentPagerAdapter {

    Checklist theOneChecklist;

    public TabAdapter(FragmentManager fm,Checklist theOneChecklist) {

        super(fm);
        this.theOneChecklist = theOneChecklist;

    }




    @Override
    public Fragment getItem(int index){


        Log.d("index","index: "+ index);

        /*
            If the Fragement is not the last fragment to display
            Display a checklist fragment
        */
        if(theOneChecklist.listCategory().length > index) {
            ChecklistFragment checkFrag = new ChecklistFragment();
            checkFrag.setCategory(theOneChecklist.listCategory()[index]);
            checkFrag.setTheOneChecklist(theOneChecklist);
            return checkFrag;
        }

        /*
            Return a save Fragment as the last fragment in the chain
         */
        else{

            SaveFragment saveFrag = new SaveFragment();
            return  saveFrag;
        }
    }



    @Override
    public int getCount() {

        // get item count - equal to number of tabs
        return theOneChecklist.listCategory().length + 1;

    }

}
