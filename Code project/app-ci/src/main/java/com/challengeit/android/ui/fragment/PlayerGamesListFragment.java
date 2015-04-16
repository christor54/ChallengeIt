package com.challengeit.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.PlayerWithGame;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.ui.activity.PlayerGameActivity;
import com.challengeit.android.ui.adapter.PlayerGamesListAdapter;

import java.util.ArrayList;
import java.util.List;

/*
 * Expose the games that the user is playing at.
 */
public class PlayerGamesListFragment extends ListFragment{
    private List<PlayerWithGame> players;

	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
        //Get the players retrieved during the app launching
        players = PersistenceSingleton.getInstance().getPlayersWithGame();
        if(players!=null) {
            ArrayList<PlayerWithGame> playerList = new ArrayList<PlayerWithGame>(players);
            PlayerGamesListAdapter adapter = new PlayerGamesListAdapter(this.getActivity(), R.layout.item_game_play, playerList);

            setListAdapter(adapter);
        }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    return inflater.inflate(R.layout.fragment_game_list, container, false);
	}
	

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this.getActivity(), PlayerGameActivity.class);
        PersistenceSingleton.getInstance().setPlayerClicked(players.get(position));
        intent.putExtra("isPlayer",true);
        startActivity(intent);
    }


}