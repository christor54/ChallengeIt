package com.challengeit.android.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.challengeit.android.R;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.ui.adapter.SelectUserListAdapter;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/*
 * Allow a user to select player among the user' friends.
 */
public class SelectFriendFragment extends ListFragment{
    @Getter @Setter
    private SelectUserListAdapter adapter;

    public static SelectFriendFragment newInstance (boolean startWithItemsChecked) {

        SelectFriendFragment selectUserFragment = new SelectFriendFragment();

        // use your custom layout
        Bundle bundle = new Bundle();
        bundle.putBoolean("startWithItemsChecked", startWithItemsChecked);
        selectUserFragment.setArguments(bundle);
        return selectUserFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        boolean startWithItemsChecked = false;
        if(savedInstanceState!=null)
            startWithItemsChecked = savedInstanceState.getBoolean("startWithItemsChecked");

        ArrayList<String> list = (ArrayList<String>) PersistenceSingleton.getInstance().getUserLocal().getAllFriendsUsername();
        if(list==null)
            list = new ArrayList<String>();
        adapter = new SelectUserListAdapter(this.getActivity(), R.layout.fragment_player_list, list, startWithItemsChecked);
        setListAdapter(adapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        adapter.toggleChecked(position);
    }
}