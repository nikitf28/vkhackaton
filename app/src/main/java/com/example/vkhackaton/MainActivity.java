package com.example.vkhackaton;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        fragmentManager = getFragmentManager();
        openedFragments.add(startFragment);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainer, startFragment);
        fragmentTransaction.commit();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeFragment();
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            closeFragment();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void logined(){
        Intent intent = new Intent(this, EnteredInAccountActivity.class);
        startActivity(intent);
        finish();
    }

}
