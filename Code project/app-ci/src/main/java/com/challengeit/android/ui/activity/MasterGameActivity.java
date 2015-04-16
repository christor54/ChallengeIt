package com.challengeit.android.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Game;
import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.persistence.PersistenceSingleton;

/*
 * Expose the list of challenges and the rank of players of a game
 */

public class MasterGameActivity extends FragmentActivity {
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = PersistenceSingleton.getInstance().getGameClicked();
        setContentView(R.layout.activity_game_master);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_master, menu);
        setTitle(game.getGameName());
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.create_challenge) {
            startCreateChallengeActicity();
            return true;
        }
        if (id == R.id.edit_challenges) {
            LogWrapper.showMessage(this,"Feature not implemented yet");
            return true;
        }
        if (id == R.id.refresh_game){
            LogWrapper.showMessage(this,"Feature not implemented yet");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startCreateChallengeActicity() {
        Intent intent = new Intent(this, CreateChallengeActivity.class);
        startActivity(intent);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_game_master, container, false);
            return rootView;
        }
    }

}
