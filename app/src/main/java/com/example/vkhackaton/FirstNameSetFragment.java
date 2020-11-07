package com.example.vkhackaton;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class FirstNameSetFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_firstnameset, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        //((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.Registration);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("");
        //((MainActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.logo_main);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        return v;
    }
}
