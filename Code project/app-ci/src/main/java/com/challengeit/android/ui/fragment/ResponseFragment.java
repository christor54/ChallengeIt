package com.challengeit.android.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Response;
import com.challengeit.android.persistence.PersistenceSingleton;

/*
 * Expose the content of a response
 */
public class ResponseFragment extends Fragment{

    private Response mResponse;

    private TextView messageTV;
    private TextView titleTV;
    private TextView descriptionTV;

    public static ResponseFragment newIntance (){
        ResponseFragment challengeFragment = new ResponseFragment();
        return  challengeFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_challenge, container, false);

        //Get the challenge object selected
        mResponse = PersistenceSingleton.getInstance().getResponseClicked();

        //Show the title
        titleTV = (TextView) rootView.findViewById(R.id.title_challenge);
        titleTV.setText(mResponse.getTitle());

        //Show the description
        descriptionTV = (TextView) rootView.findViewById(R.id.description_challenge);
        if(descriptionTV.getText()!=null&&mResponse.getDescription()!=null)
            descriptionTV.setText(mResponse.getDescription());
        else
            titleTV.setText("no description in this challenge");

        //Show the message
        messageTV = (TextView) rootView.findViewById(R.id.content_message_challenge);
        if(mResponse.getMessage()!=null&&mResponse.getMessage().getText()!=null)
            messageTV.setText(mResponse.getMessage().getText());
        else
            messageTV.setText("no message in this challenge");
        return rootView;
    }
}
