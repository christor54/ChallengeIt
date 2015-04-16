package com.challengeit.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.ui.activity.MainActivity;
import com.challengeit.android.ui.fragment.FriendsFragment;
import com.challengeit.android.ui.fragment.MainSettingsFragment;
import com.challengeit.android.ui.fragment.MasterGamesListFragment;
import com.challengeit.android.ui.fragment.PlayerGamesListFragment;


///**
//* A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
//* sections of the app.
//*/
public class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {
    public final int MASTER_FRAGMENT_NUMBER = 0;
    public final int PLAYER_FRAGMENT_NUMBER = 1;
    public final int FRIEND_FRAGMENT_NUMBER = 2;
    public final int PROFILE_FRAGMENT_SETTING = 3;
    Fragment cameraFragment;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    public MainFragmentPagerAdapter(FragmentManager fm, Fragment camFrag) {
        super(fm);
        cameraFragment = camFrag;
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case MASTER_FRAGMENT_NUMBER://To the very left
                LogWrapper.debug(getClass(), "item " + i);
                return MasterGamesListFragment.getInstance();
            case PLAYER_FRAGMENT_NUMBER:
                LogWrapper.debug(getClass(), "item " + i);
                return new PlayerGamesListFragment();
            case FRIEND_FRAGMENT_NUMBER:
                LogWrapper.debug(getClass(), "item " + i);
                return new FriendsFragment();
            case PROFILE_FRAGMENT_SETTING:
                LogWrapper.debug(getClass(), "item " + i);
                return new MainSettingsFragment();
            default:
                LogWrapper.error(getClass(), "item " + i);
                return new PlayerGamesListFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
       switch (position){
           case MASTER_FRAGMENT_NUMBER :
               return "Master";
           case PLAYER_FRAGMENT_NUMBER :
               return "Play";
           case FRIEND_FRAGMENT_NUMBER :
               return "Friend";
           case PROFILE_FRAGMENT_SETTING :
               return "Profile";
           default:
               return "No";

       }
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return MainActivity.NUM_ITEMS;
    }

}