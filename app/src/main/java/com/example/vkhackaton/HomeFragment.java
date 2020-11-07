package com.example.vkhackaton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    View v;
    TextView name, surname, date;
    String token;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.Home);
        ((EnteredInAccountActivity)getActivity()).setToolbar(toolbar);
        setHasOptionsMenu(true);
        ((EnteredInAccountActivity)getActivity()).getDrawerToggle().setToolbarNavigationClickListener(null);
        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(ApplicationConstants.TOKEN, "");

        name = v.findViewById(R.id.yourName);
        surname = v.findViewById(R.id.yourSurname);
        date = v.findViewById(R.id.yourBirthDate);
        loadData();

        return v;
    }

    public void loadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final UserInfo userInfo = APIConnector.profileMeGet(token);
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            name.setText(userInfo.firstName);
                            surname.setText(userInfo.lastName);
                            date.setText(userInfo.age + " " + getString(R.string.ages));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        //((EnteredInAccountActivity)getActivity()).setToolbar(null);
        super.onDestroyView();
    }

}
