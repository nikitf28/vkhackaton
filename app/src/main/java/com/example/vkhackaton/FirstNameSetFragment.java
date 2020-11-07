package com.example.vkhackaton;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import static android.content.Context.MODE_PRIVATE;

public class FirstNameSetFragment extends Fragment {

    EditText nameET, surnameET, dateET;
    Button confirmBTN;
    String token;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_firstnameset, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        //((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.Registration);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("");
        //((MainActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.logo_main);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        nameET = v.findViewById(R.id.nameET);
        surnameET = v.findViewById(R.id.surnameET);
        dateET = v.findViewById(R.id.dateET);
        confirmBTN = v.findViewById(R.id.setfirstnameBTN);
        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences(ApplicationConstants.PREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString(ApplicationConstants.TOKEN, "");
        confirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo();
            }
        });
        return v;
    }

    public void setInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String[] message = APIConnector.profileMePost(token, nameET.getText().toString(), surnameET.getText().toString(), dateET.getText().toString(), "");
                    if (message[0].equals("OK")){
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                ((MainActivity)getActivity()).logined();
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

}
