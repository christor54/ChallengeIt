package com.challengeit.android.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.challengeit.android.R;

import lombok.Getter;
import lombok.Setter;

/*
 * Create a simple dummy fragment that display a text.
 */
public class DummyFragment extends Fragment {
    @Getter @Setter
    private String description;

    public static DummyFragment newIntance (String description){
        DummyFragment dummyFragment = new DummyFragment();
        dummyFragment.setDescription(description);
        return  dummyFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                description);
        return rootView;
    }

}