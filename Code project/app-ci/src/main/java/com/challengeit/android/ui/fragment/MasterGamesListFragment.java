package com.challengeit.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.MasterWithGame;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.ui.activity.MasterGameActivity;
import com.challengeit.android.ui.adapter.MasterGamesListAdapter;

import java.util.ArrayList;
import java.util.List;

/*
 * Expose the games that the user is mastering
 */
public class MasterGamesListFragment extends ListFragment{
    private static MasterGamesListFragment instance;
    private List<MasterWithGame> masters;
    private MasterGamesListAdapter adapter;

    public static MasterGamesListFragment getInstance( ){
        if(instance==null)
            instance = new MasterGamesListFragment();
        return instance;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        masters = PersistenceSingleton.getInstance().getMastersWithGame();
        ArrayList<MasterWithGame> masterlist;
        if(masters!=null) {
            masterlist= new ArrayList<>(masters);
        }
        else{
            masterlist = new ArrayList<>();
        }
        adapter = new MasterGamesListAdapter(this.getActivity(), R.layout.item_game_master, masterlist);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_master_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this.getActivity(), MasterGameActivity.class);
        PersistenceSingleton.getInstance().setMasterClicked(masters.get(position));
        startActivity(intent);
    }

    public void addGame(MasterWithGame masterWithGame) {
        PersistenceSingleton.getInstance().add(masterWithGame);
        adapter.addGame(masterWithGame);
    }
}