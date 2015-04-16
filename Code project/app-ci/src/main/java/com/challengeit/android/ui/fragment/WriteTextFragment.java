package com.challengeit.android.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.challengeit.android.R;
/**
 * 
 */
public class WriteTextFragment extends Fragment {
    private String description;
    private EditText textMessageEditText;

    public static WriteTextFragment newInstance (String description){
        WriteTextFragment writeTextFragment =  new WriteTextFragment();
        writeTextFragment.setDescription(description);
        return writeTextFragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
        textMessageEditText = (EditText) rootView.findViewById(R.id.editTextMessage);

        return rootView;
    }



    public EditText getTextMessageEditText() {
        return textMessageEditText;
    }

    public void setTextMessageEditText(EditText textMessageEditText) {
        this.textMessageEditText = textMessageEditText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
