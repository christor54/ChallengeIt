package com.challengeit.android.ui.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.gameEndpoint.GameEndpoint;
import com.challengeit.android.challengeit.gameEndpoint.model.Challenge;
import com.challengeit.android.challengeit.gameEndpoint.model.Message;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Game;
import com.challengeit.android.challengeit.userLocalEndpoint.model.UserLocal;
import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.util.CloudEndpointUtils;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


/**
 * This class allows the user to create a challenge
 */


public class CreateChallengeActivity extends FragmentActivity {

    //GUIs
    private EditText titleET;
    private EditText descriptionET;
    private EditText contentET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate and instantiate UIs
        setContentView(R.layout.activity_create_challenge);
        titleET = (EditText)findViewById(R.id.title_challenge);
        descriptionET = (EditText) findViewById(R.id.description_challenge);
        contentET = (EditText) findViewById(R.id.content_message);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_challenge, menu);
        setTitle("Create Challenge");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.validate_challenge) {
            Challenge challenge = new Challenge();
            String title = titleET.getText().toString();
            String description = descriptionET.getText().toString();
            String content = contentET.getText().toString();
            if(title==null) {
                LogWrapper.showMessage(this, "Title can't be empty");
                return true;
            }
            challenge.setTitle(title);
            Message message = new Message();
            message.setText(content);
            challenge.setDescription(description);
            challenge.setMessage(message);
            Game game = PersistenceSingleton.getInstance().getGameClicked();
            new AddChallengeTask(this).execute((new Pair<Game, Challenge>(game, challenge)));
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Add a challenge in the database
     */

    private class AddChallengeTask extends AsyncTask<Pair<Game,Challenge>, Integer, String> {
        private Activity activity;

        public AddChallengeTask(Activity activity){
            this.activity =  activity;
        }


        @Override
        protected String doInBackground(Pair<Game,Challenge>... params) {
            GameEndpoint.Builder builder = new GameEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),
                    null);
            builder = CloudEndpointUtils.updateBuilder(builder);
            GameEndpoint endpoint = builder.build();

            Pair<Game, Challenge> param = params[0];
            UserLocal userLocal;
            String message = null;

            try {
                endpoint.addChallenge(param.first.getId(), param.second).execute();
                message ="challenge_player : "+ param.second.getTitle() +" was created";
            } catch (IOException e) {
                message = LogWrapper.getMessageFromException(e);
            }
            return message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

}
