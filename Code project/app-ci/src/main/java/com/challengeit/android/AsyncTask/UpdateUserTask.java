package com.challengeit.android.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.challengeit.android.challengeit.userLocalEndpoint.UserLocalEndpoint;
import com.challengeit.android.challengeit.userLocalEndpoint.model.UserLocal;
import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.util.CloudEndpointUtils;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 *
 * To be used like this :
 *
 *         UserLocal updatedUser =  new UpdateUserTask(this).execute(userLocal).get();
 */
public class UpdateUserTask extends AsyncTask<UserLocal, Integer, UserLocal> {
    private Context context;

    public UpdateUserTask(Context context){
        this.context=context;
    }

    @Override
    protected UserLocal doInBackground(UserLocal... params) {
        UserLocalEndpoint.Builder builder = new UserLocalEndpoint.Builder(
                AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),
                null);
        builder = CloudEndpointUtils.updateBuilder(builder);
        UserLocalEndpoint endpoint = builder.build();

        UserLocal userLocal = null;

        try {
            userLocal = endpoint.update(params[0]).execute();
        } catch (IOException e) {
            if (e != null) {
                String message = e.getMessage();
                if (message == null) {
                    message = e.toString();
                }
                LogWrapper.error(getClass(), message);
            }
            userLocal = null;
        }
        return userLocal;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(UserLocal userLocal) {
        super.onPostExecute(userLocal);
        Toast toast = Toast.makeText(context, "User " + userLocal.getUsername() +" updated", Toast.LENGTH_SHORT);
        toast.show();
    }
}
