package com.challengeit.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Response;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.ui.activity.ResponseActivity;
import com.challengeit.android.ui.adapter.ResponsesListAdapter;

import java.util.ArrayList;

/*
 * Expose the list of responses of a challenge
 */
public class ResponsesListFragment extends ListFragment {
    private ArrayList<Response> responses;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PersistenceSingleton persist = PersistenceSingleton.getInstance();
        responses = (ArrayList<Response>) persist.getChallengeClicked().getResponses();
        if(responses ==null)
            responses = new  ArrayList<>();
        ResponsesListAdapter adapter = new ResponsesListAdapter(this.getActivity(), R.layout.item_response, responses);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_response_list, container, false);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this.getActivity(), ResponseActivity.class);
        PersistenceSingleton.getInstance().setResponseClicked(responses.get(position));
        startActivity(intent);
    }
}
