package com.challengeit.android.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.UserLocalEndpoint;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Game;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Player;
import com.challengeit.android.challengeit.userLocalEndpoint.model.UserLocal;
import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.ui.adapter.CreateGameSectionsPagerAdapter;
import com.challengeit.android.ui.fragment.CreateGameSettingsFragment;
import com.challengeit.android.ui.fragment.SelectFriendFragment;
import com.challengeit.android.util.CloudEndpointUtils;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class allows the user to create a Game, and saves it in the database
 */
public class CreateGameActivity extends FragmentActivity implements ActionBar.TabListener {

    CreateGameSectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);


        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new CreateGameSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_game, menu);
        // Hide the new_attachment icon
        menu.findItem(R.id.validation).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.validation :
                try {
                    createGame();
                } catch (Exception e) {
                    LogWrapper.logAndShowError(this,e);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void createGame() throws Exception {
        CreateGameSettingsFragment createGameSettingsFragment = (CreateGameSettingsFragment) mSectionsPagerAdapter.getRegisteredFragment(0) ;
        String title= createGameSettingsFragment.getGameTitle();
        String description=createGameSettingsFragment.getGameDescription();
        game = new Game();
        if(title==null||title.equals(""))
            throw new Exception("add a game_player title");
        game.setGameName(title);
        if(description!=null)
            game.setDescription(description);
        List<String> playersToAdd = ((SelectFriendFragment)mSectionsPagerAdapter.getRegisteredFragment(1)).getAdapter().getCheckedItems();
        game = insertPlayersFromUsernamesIntoGame(playersToAdd,game);
        if(game.getPlayers()==null||game.getPlayers().size()==0)
            throw new Exception("add players in your game_player");
        new InsertGameTask(this).execute(game);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game insertPlayersFromUsernamesIntoGame(List<String> playersUsername,Game game ){
        Player player;
        List<Player> listPlayers = new ArrayList<>();
        for(String username : playersUsername ){
            player = new Player();
            player.setUsername(username);
            listPlayers.add(player);
        }
        if(game!=null)
            game.setPlayers(listPlayers);
        return game;
    }

    private class InsertGameTask extends AsyncTask<Game, Integer, String> {
        private Activity activity;

        public InsertGameTask(Activity activity){
            this.activity =  activity;
        }


        @Override
        protected String doInBackground(Game... params) {
            UserLocalEndpoint.Builder builder = new UserLocalEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),
                    null);
            builder = CloudEndpointUtils.updateBuilder(builder);
            //builder = CloudEndpointUtils.updateBuilder(builder);
            UserLocalEndpoint endpoint = builder.build();

            Game game = params[0];
            UserLocal userLocal;
            String message = null;

            try {
                userLocal = endpoint.insertGame(game).execute();
                PersistenceSingleton.getInstance().setUserLocal(userLocal);
                PersistenceSingleton.getInstance().setGameAdded(game);
                message = userLocal.getUsername() +" is the master of " + game.getGameName();
            } catch (IOException e) {
                message = LogWrapper.geMessageFromExceptionWithBackup(e,"game couldn't be created");
            }
            return message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String message) {
            Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
            toast.show();
            NavUtils.navigateUpFromSameTask(activity);
        }
    }

}
