package com.challengeit.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Game;
import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.persistence.PersistenceSingleton;

/*
* Expose the responses made for a challenge
* */
public class ResponsesListActivity extends FragmentActivity {
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = PersistenceSingleton.getInstance().getGameClicked();
        setContentView(R.layout.activity_responses_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.responses, menu);
        setTitle(game.getGameName());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_responses) {
            LogWrapper.showMessage(this, "Not implemented yet");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startCreateChallengeActicity() {
        Intent intent = new Intent(this, CreateChallengeActivity.class);
        startActivity(intent);
    }

}
