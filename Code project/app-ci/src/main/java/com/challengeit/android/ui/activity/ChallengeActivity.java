package com.challengeit.android.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Challenge;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.ui.fragment.ChallengeFragment;

/**
 * This class displays the content of a challenge
 */
public class ChallengeActivity extends Activity {
    private Challenge challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  challenge_player= (Challenge) getIntent().getSerializableExtra("challenge_player");
        setContentView(R.layout.activity_challenge);
        challenge = PersistenceSingleton.getInstance().getChallengeClicked();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, ChallengeFragment.newIntance())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.challenge_player, menu);
        if(challenge.getTitle()==null)
            setTitle("challenge_player");
        else
            setTitle(challenge.getTitle());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.respond_challenge) {
            startCreateResponseActicity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startCreateResponseActicity() {
        Intent intent = new Intent(this, CreateResponseActivity.class);
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
            View rootView = inflater.inflate(R.layout.fragment_challenge, container, false);
            return rootView;
        }
    }
}
