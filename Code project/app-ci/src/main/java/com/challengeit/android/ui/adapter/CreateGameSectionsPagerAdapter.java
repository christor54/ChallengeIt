package com.challengeit.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.challengeit.android.challengeit.userLocalEndpoint.model.UserLocal;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.ui.fragment.CreateGameSettingsFragment;
import com.challengeit.android.ui.fragment.SelectFriendFragment;

import java.util.Locale;

public class CreateGameSectionsPagerAdapter extends FragmentPagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    public CreateGameSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position){
            case 0 :
                return CreateGameSettingsFragment.newInstance();
            case 1 :
                UserLocal userLocal = PersistenceSingleton.getInstance().getUserLocal();
                return SelectFriendFragment.newInstance(false);
            default:
                return SelectFriendFragment.newInstance(false);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "Settings";
            case 1:
                return "Players";
        }
        return null;
    }
}