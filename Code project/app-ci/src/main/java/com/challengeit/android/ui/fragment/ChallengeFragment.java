package com.challengeit.android.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Challenge;
import com.challengeit.android.persistence.PersistenceSingleton;

/*
 * Expose the content of a challenge
 */
public class ChallengeFragment extends Fragment{

    private Challenge mChallenge;

    private TextView messageTV;
    private TextView titleTV;
    private TextView descriptionTV;

    public static ChallengeFragment newIntance (){
        ChallengeFragment challengeFragment = new ChallengeFragment();
        return  challengeFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_challenge, container, false);

        //Get the challenge object selected
        mChallenge = PersistenceSingleton.getInstance().getChallengeClicked();

        //Show the title
        titleTV = (TextView) rootView.findViewById(R.id.title_challenge);
        titleTV.setText(mChallenge.getTitle());

        //Show the description
        descriptionTV = (TextView) rootView.findViewById(R.id.description_challenge);
        if(descriptionTV.getText()!=null&&mChallenge.getDescription()!=null)
            descriptionTV.setText(mChallenge.getDescription());
        else
            titleTV.setText("no description in this challenge");

        //Show the message
        messageTV = (TextView) rootView.findViewById(R.id.content_message_challenge);
        if(mChallenge.getMessage()!=null&&mChallenge.getMessage().getText()!=null)
            messageTV.setText(mChallenge.getMessage().getText());
        else
            messageTV.setText("no message in this challenge");
        return rootView;
    }
}
