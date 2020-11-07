package com.example.vkhackaton;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class FriendsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.Friends);
        ((EnteredInAccountActivity)getActivity()).setToolbar(toolbar);
        ((EnteredInAccountActivity)getActivity()).getDrawerToggle().setToolbarNavigationClickListener(null);
        return v;
    }
    @Override
    public void onDestroyView() {
        //((EnteredInAccountActivity)getActivity()).setToolbar(null);
        super.onDestroyView();
    }
}
