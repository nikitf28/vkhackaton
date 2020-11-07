package com.example.vkhackaton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Fragment;
import android.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StartFragment extends Fragment {

    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_start, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        //((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.Registration);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("");
        //((MainActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.logo_main);
        Button goToLoginBTN = v.findViewById(R.id.goToLoginBTN);
        Button goToRegisterBTN = v.findViewById(R.id.goToRegisterBTN);
        goToLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).goToLogin();
            }
        });
        goToRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).goToRegister();
            }
        });
        return v;
    }
}
