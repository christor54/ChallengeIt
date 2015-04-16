package com.challengeit.android.ui.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.gameEndpoint.GameEndpoint;
import com.challengeit.android.challengeit.gameEndpoint.model.Game;
import com.challengeit.android.challengeit.gameEndpoint.model.Message;
import com.challengeit.android.challengeit.gameEndpoint.model.Response;
import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.util.CloudEndpointUtils;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * This class allows the user to create a response, and add it on the corresponding game in the database
 */
public class CreateResponseActivity extends FragmentActivity {

    //GUIs
    private EditText titleET;
    private EditText descriptionET;
    private EditText contentET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate view and instantiate GUIs
        setContentView(R.layout.activity_create_response);
        titleET = (EditText)findViewById(R.id.title_response);
        descriptionET = (EditText) findViewById(R.id.description_response);
        contentET = (EditText) findViewById(R.id.content_message_response);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_response, menu);
        setTitle("Response");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.validate_response) {

            //Create response and affect the value entered by the user
            Response response = new Response();
            String title = titleET.getText().toString();
            String description = descriptionET.getText().toString();
            String content = contentET.getText().toString();
            if(title==null) {
                LogWrapper.showMessage(this, "Title can't be empty");
                return true;
            }
            response.setTitle(title);
            Message message = new Message();
            message.setText(content);
            response.setDescription(description);
            response.setMessage(message);

            //Add the response in the database
            new AddResponseTask(this).execute(response);

            //Go back to the parent activity
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Add the response on the underlying challenge in the database.
     */
    private class AddResponseTask extends AsyncTask<Response, Integer, String> {
        private Activity activity;


        public AddResponseTask(Activity activity){
            this.activity =  activity;
        }


        @Override
        protected String doInBackground(Response... params) {
            GameEndpoint.Builder builder = new GameEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),
                    null);
            builder = CloudEndpointUtils.updateBuilder(builder);
            GameEndpoint endpoint = builder.build();

            PersistenceSingleton persistenceSingleton = PersistenceSingleton.getInstance();
            Long gameId = persistenceSingleton.getGameClicked().getId();
            String challengeTitle = persistenceSingleton.getChallengeClicked().getTitle();
            Response response = params[0];
            String message = null;

            try {
                Game game = endpoint.respondToChallenge(gameId,challengeTitle,response).execute();
                message ="Response : "+ response.getTitle() +" was created";
                //Update the core data.
            } catch (IOException e) {
                message = LogWrapper.geMessageFromExceptionWithBackup(e,"response couldn't be created");
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
