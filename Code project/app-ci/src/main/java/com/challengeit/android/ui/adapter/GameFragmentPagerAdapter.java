package com.challengeit.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.ui.fragment.ChallengesListFragment;


public class GameFragmentPagerAdapter extends FragmentPagerAdapter {

    public GameFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0://Camera to the left
                LogWrapper.debug(getClass(), "item " + i);
                return new ChallengesListFragment();

            default:
                LogWrapper.error(getClass(), "default");
                return new ChallengesListFragment();

        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 1;
    }
}



