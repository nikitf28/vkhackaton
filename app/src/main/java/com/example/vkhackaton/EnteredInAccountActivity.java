package com.example.vkhackaton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class EnteredInAccountActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    List<Fragment> openedFragments = new ArrayList<Fragment>();
    boolean main;
    EditProfileFragment editProfileFragment = new EditProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_entered_in_account);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        // This will display an Up icon (<-), we will replace it with hamburger later

        // Find our drawer view
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        openedFragments.add(new HomeFragment());
        fragmentTransaction.replace(R.id.flContent, new HomeFragment()).commit();
        /*if(toolbar!=null){
        drawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);}*/
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return false;
            }
        });
        getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);

    }
    public void setToolbar(Toolbar toolbar) {
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            drawerToggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(drawerToggle);
            drawerToggle.syncState();
        } else {
            drawer.setDrawerListener(null);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            closeFragment();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public ActionBarDrawerToggle getDrawerToggle(){
        return drawerToggle;
    }

    public void logout(){
        SharedPreferences prefs = getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(ApplicationConstants.TOKEN);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void confirmEditProfile(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setTitle(getString(R.string.changeProfileAlert));
        builder.setMessage("");
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editProfileFragment.setInfo();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        boolean change = true;
        switch(menuItem.getItemId()) {
            case R.id.home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.friends:
                fragmentClass = FriendsFragment.class;
                break;
            case R.id.editProfile:
                fragmentClass = EditProfileFragment.class;
                break;
            case R.id.logout:
                logout();
                fragmentClass = HomeFragment.class;
                break;
            case R.id.confirmEditCode:
                confirmEditProfile();
                change = false;
                fragmentClass = EditProfileFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        if (change) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                if (menuItem.getItemId() == R.id.editProfile){
                    editProfileFragment = (EditProfileFragment)fragment;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            openedFragments.add(fragment);
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.replace(R.id.flContent, fragment).commit();

            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());

            // Close the navigation drawer
            invalidateOptionsMenu();
            drawer.closeDrawers();
        }
    }

    public void closeFragment(){
        if (openedFragments.size() > 1){
            openedFragments.remove(openedFragments.size()-1);
            FragmentManager fragmentManager;
            FragmentTransaction fragmentTransaction;
            FragmentManager _fragmentManager = getSupportFragmentManager();
            FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
            _fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            _fragmentTransaction.replace(R.id.flContent, openedFragments.get(openedFragments.size()-1)).commit();
        }else{
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        selectDrawerItem(item);
        return true;
    }

    private FragmentManager.OnBackStackChangedListener
            mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            syncActionBarArrowState();
        }
    };

    private void syncActionBarArrowState() {
        int backStackEntryCount =
                getSupportFragmentManager().getBackStackEntryCount();
        drawerToggle.setDrawerIndicatorEnabled(backStackEntryCount == 0);
    }

}
