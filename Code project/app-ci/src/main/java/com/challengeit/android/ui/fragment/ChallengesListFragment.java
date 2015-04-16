package com.challengeit.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Challenge;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.ui.activity.ChallengeActivity;
import com.challengeit.android.ui.activity.ResponsesListActivity;
import com.challengeit.android.ui.adapter.ChallengesListAdapter;

import java.util.ArrayList;

/*
 * Expose the list of challenge of a game
 */

public class ChallengesListFragment extends ListFragment {
    private ArrayList<Challenge> challenges;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PersistenceSingleton persist = PersistenceSingleton.getInstance();
        challenges = (ArrayList<Challenge>) persist.getGameClicked().getChallenges();
        if(challenges==null)
            challenges = new ArrayList<Challenge>();
        ChallengesListAdapter adapter = new ChallengesListAdapter(this.getActivity(), R.layout.item_challenge_player, challenges);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_challenge_list, container, false);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent;
        if(PersistenceSingleton.getInstance().getIsMasterClicked()) {
            intent = new Intent(this.getActivity(), ResponsesListActivity.class);
            PersistenceSingleton.getInstance().setChallengeClicked(challenges.get(position));

        }else {
            intent = new Intent(this.getActivity(), ChallengeActivity.class);
            PersistenceSingleton.getInstance().setChallengeClicked(challenges.get(position));
        }
        startActivity(intent);
    }
}
