package com.example.vkhackaton;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class EditProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.EditProfile);
        ((EnteredInAccountActivity)getActivity()).setToolbar(toolbar);
        setHasOptionsMenu(true);
        /*((EnteredInAccountActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((EnteredInAccountActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        /*((EnteredInAccountActivity)getActivity()).getDrawerToggle().setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EnteredInAccountActivity)getActivity()).closeFragment();
            }
        });*/
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.editprofile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        //((EnteredInAccountActivity)getActivity()).setToolbar(null);
        super.onDestroyView();
    }
}
