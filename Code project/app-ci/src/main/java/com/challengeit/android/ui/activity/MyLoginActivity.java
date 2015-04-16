package com.challengeit.android.ui.activity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.UserLocalEndpoint;
import com.challengeit.android.challengeit.userLocalEndpoint.model.CollectionResponseMasterWithGame;
import com.challengeit.android.challengeit.userLocalEndpoint.model.CollectionResponsePlayerWithGame;
import com.challengeit.android.challengeit.userLocalEndpoint.model.MasterWithGame;
import com.challengeit.android.challengeit.userLocalEndpoint.model.PlayerWithGame;
import com.challengeit.android.challengeit.userLocalEndpoint.model.UserLocal;
import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.util.CloudEndpointUtils;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.IOException;
import java.util.List;

/*
 * Allow the user to sign in into the app by using the user Google Account credential.
 * Retrieve the User object if authenticated.
 */
public class MyLoginActivity extends Activity {
    private static final String WEB_CLIENT_ID = "878357024341-jqvtsl3kmnfat6rue4ifhevje9t6qdsu.apps.googleusercontent.com";
    private static final int REQUEST_ACCOUNT_PICKER = 3;
    private SharedPreferences settings;
    private GoogleAccountCredential credential;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate view
        setContentView(R.layout.welcome_page);

        //Get app shared preferences
        settings = getSharedPreferences("ChallengeIt", 0);

        //Get Google Account Credential
        credential = GoogleAccountCredential.usingAudience(MyLoginActivity.this, "server:client_id:" + WEB_CLIENT_ID);
        setAccountName(settings.getString("ACCOUNT_NAME", null));

        // if already signed in, retrieve user from the database and begin app
        if (credential.getSelectedAccountName() != null) {
            Toast.makeText(getBaseContext(), "Logged in with : " + credential.getSelectedAccountName(), Toast.LENGTH_SHORT).show();
            CloudEndpointUtils.setCredential(credential);
            new getUserTask(this).execute();
        }

        // if not signed in, show login window or request an account.
        else {
            chooseAccount();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setAccountName(String accountName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ACCOUNT_NAME", accountName);
        editor.commit();
        credential.setSelectedAccountName(accountName);
    }

    void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (data != null && data.getExtras() != null) {
                    String accountName =
                            data.getExtras().getString(
                                    AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        setAccountName(accountName);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("ACCOUNT_NAME", accountName);
                        editor.commit();
                    }
                }
                break;
        }
    }

    private class insertUserTask extends AsyncTask<UserLocal, Integer, String> {
        private Activity activity;

        public insertUserTask(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(UserLocal... params) {
            CollectionResponseMasterWithGame collectionResponseMasterWithGame;
            List<MasterWithGame> listM;
            CollectionResponsePlayerWithGame collectionResponsePlayerWithGame;
            List<PlayerWithGame> listP;
            String message;
            UserLocalEndpoint.Builder builder = new UserLocalEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),
                    null);
            builder = CloudEndpointUtils.updateBuilder(builder);
            UserLocalEndpoint endpoint = builder.build();

            UserLocal userLocalT=params[0];
            try {
                userLocalT= endpoint.insert(userLocalT).execute();
                PersistenceSingleton.getInstance().setUserLocal(userLocalT);
                message="Welcome " +userLocalT.getUsername();
                startMainActivity();
            } catch (IOException e) {
                message= LogWrapper.geMessageFromExceptionWithBackup(e,"User couldn't be inserted");
            }
            return message;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onPostExecute(String message) {
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
            toast.show();
        }
    }
    private class getUserTask extends AsyncTask<Void, Integer, UserLocal> {
        private Activity activity;

        public getUserTask(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected UserLocal doInBackground(Void... params) {
            CollectionResponseMasterWithGame collectionResponseMasterWithGame;
            List<MasterWithGame> listM;
            CollectionResponsePlayerWithGame collectionResponsePlayerWithGame;
            List<PlayerWithGame> listP;
            UserLocalEndpoint.Builder builder = new UserLocalEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),
                    credential);
            builder = CloudEndpointUtils.updateBuilder(builder);
            UserLocalEndpoint endpoint = builder.build();
            UserLocal userLocalT=null;

            try {
                userLocalT= endpoint.get().execute();
                PersistenceSingleton.getInstance().setUserLocal(userLocalT);
                collectionResponseMasterWithGame= endpoint.listMastersWithGame().execute();
                listM=collectionResponseMasterWithGame.getItems();
                PersistenceSingleton.getInstance().setMastersWithGame(listM);
                collectionResponsePlayerWithGame = endpoint.listPlayersWithGames().execute();
                listP = collectionResponsePlayerWithGame.getItems();
                PersistenceSingleton.getInstance().setPlayersWithGame(listP);
            } catch (IOException e) {
                CloudEndpointUtils.logAndShowError(activity,e);
            }
            return userLocalT;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onPostExecute(UserLocal userLocal) {
            String toastMessage;
            if(userLocal!=null) {
                toastMessage="Welcome back " +userLocal.getUsername();
                startMainActivity();
            }else {
                toastMessage="Creating new user";
                UserLocal newUserLocal = new UserLocal();
                new insertUserTask(activity).execute(newUserLocal);
            }
            Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
