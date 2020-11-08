package com.example.vkhackaton;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int fragmentContainer = R.id.fragment_container;
    RegistrationFragment registrationFragment = new RegistrationFragment();
    AuthenticationFragment authenticationFragment = new AuthenticationFragment();
    StartFragment startFragment = new StartFragment();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    List<Fragment> openedFragments = new ArrayList<Fragment>();
    View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        final String token;
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(ApplicationConstants.PREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString(ApplicationConstants.TOKEN, "");
        if (!token.equals("")){
            logined();
        }
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        fragmentManager = getFragmentManager();
        openedFragments.add(startFragment);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainer, startFragment);
        fragmentTransaction.commit();
        v = findViewById(R.id.layout);
    }

    public void goToLogin(){
        openFragment(authenticationFragment);
    }

    public void goToRegister(){
        openFragment(registrationFragment);
    }

    public void openFragment(Fragment _fragment){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        openedFragments.add(_fragment);
        fragmentTransaction.replace(fragmentContainer, _fragment);
        fragmentTransaction.commit();
    }

    public void closeFragment(){
        if (openedFragments.size() > 1){
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            openedFragments.remove(openedFragments.size()-1);
            fragmentTransaction.replace(fragmentContainer, openedFragments.get(openedFragments.size()-1));
            fragmentTransaction.commit();
        }else{
            finish();
        }
    }

    public void login(){
        final String token;
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(ApplicationConstants.PREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString(ApplicationConstants.TOKEN, "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final UserInfo user = APIConnector.profileMeGet(token);
                    if (user.status.equals("OK")){
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                logined();
                            }
                        });
                    }else if (user.ERROR.equals("PROFILE_NOT_READY")){
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                openFragment(new FirstNameSetFragment());
                            }
                        });
                    }else{
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), user.ERROR, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void logout(){
        SharedPreferences prefs = getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(ApplicationConstants.TOKEN);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeFragment();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    public void logined(){
        Intent intent = new Intent(this, EnteredInAccountActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            closeFragment();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
