package com.challengeit.android.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.UserLocalEndpoint;
import com.challengeit.android.challengeit.userLocalEndpoint.model.UserLocal;
import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.ui.adapter.SelectUserListAdapterNames;
import com.challengeit.android.util.CloudEndpointUtils;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Allow a user to manage his friends : add friend and view his list of friends
 */
public class FriendsFragment extends ListFragment{
    private final boolean startWithItemsChecked = false;
    private UserLocal user;
    private SelectUserListAdapterNames adapter;
    private EditText usernameFriendToAdd;
    private Button addAFriendButton;


    public static FriendsFragment newInstance () {
        FriendsFragment selectUserFragment = new FriendsFragment();
        return selectUserFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = PersistenceSingleton.getInstance().getUserLocal();
        List<String> users=null;
        try {
             users = user.getAllFriendsUsername();
        }catch (Exception e){

        }
        ArrayList<String> usersA;
        if(users!=null)
            usersA = new ArrayList<>(users);
        else
            usersA = new ArrayList<>();
        adapter = new SelectUserListAdapterNames(this.getActivity(), R.layout.fragment_friends, usersA, startWithItemsChecked);
        setListAdapter(adapter);

        addAFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameFriend = usernameFriendToAdd.getText().toString();
                new AddFriendTask().execute(usernameFriend);
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_friends, container, false);
        usernameFriendToAdd = (EditText) rootView.findViewById(R.id.add_a_friend);
        addAFriendButton = (Button) rootView.findViewById(R.id.add_a_friend_button);
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        adapter.toggleChecked(position);
    }

    private class AddFriendTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            UserLocalEndpoint.Builder builder = new UserLocalEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),
                    null);
            builder = CloudEndpointUtils.updateBuilder(builder);
            UserLocalEndpoint endpoint = builder.build();

            String message;
            UserLocal userLocal = null;
            String friendUsername = params[0];

            try {
                userLocal = endpoint.addFriend(friendUsername).execute();
                PersistenceSingleton.getInstance().setUserLocal(userLocal);
                adapter.addFriend(usernameFriendToAdd.getText().toString());
                message= "friend "+usernameFriendToAdd.getText().toString()+" added to account " +userLocal.getUsername();
            } catch (IOException e) {
                LogWrapper.error(FriendsFragment.class, e.getMessage());
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
            Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}