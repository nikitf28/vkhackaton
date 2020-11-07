package com.example.vkhackaton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class EditProfileFragment extends Fragment {

    EditText name, surname, date;
    String token;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
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
        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(ApplicationConstants.TOKEN, "");
        name = v.findViewById(R.id.yourName);
        surname = v.findViewById(R.id.yourSurname);
        date = v.findViewById(R.id.yourBirthDate);
        loadData();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.editprofile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void setInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String[] message = APIConnector.profileMePost(token, name.getText().toString(), surname.getText().toString(), date.getText().toString(), "");
                    if (message[0].equals("OK")){
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                loadData();
                            }
                        });
                    }else{
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), message[1], Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        //((EnteredInAccountActivity)getActivity()).setToolbar(null);
        super.onDestroyView();
    }
}
