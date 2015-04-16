package com.challengeit.android.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.challengeit.android.R;

/*
 * ALlow the user to select the game settings in the CreateGameActivity
 */
public class CreateGameSettingsFragment extends Fragment {
    public static final String ARG_SECTION_NUMBER = "section_number";
    private EditText gameTitle;
    private EditText gameDescription;

    public static CreateGameSettingsFragment newInstance (){
        return new CreateGameSettingsFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_game_settings, container, false);
        gameTitle= (EditText) rootView.findViewById(R.id.game_title);
        gameDescription= (EditText)rootView.findViewById(R.id.game_description);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public String getGameTitle(){
        Editable editable=null;
        String string=null;
        if(gameTitle!=null)
            editable=gameTitle.getText();
        if(editable!=null)
            string=editable.toString();
        return string;
    }

    public String getGameDescription(){
        Editable editable=null;
        String string=null;
        if(gameDescription!=null)
            editable=gameDescription.getText();
        if(editable!=null)
            string=editable.toString();
        return string;
    }
}