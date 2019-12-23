package com.peargrammers.flashcards.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.authentication.NavBar;
import com.peargrammers.flashcards.fragments.ContactFragment;
import com.peargrammers.flashcards.fragments.EditFragment;
import com.peargrammers.flashcards.fragments.HomeFragment;
import com.peargrammers.flashcards.fragments.UserFragment;
import com.peargrammers.flashcards.viewmodels.management.HomeViewModel;

public class HomeActivity extends AppCompatActivity {

    HomeViewModel homeViewModel;
    private TextView tv_username;
    SpaceNavigationView navigationView;
    ContactFragment contactFragment;
    HomeFragment homeFragment;
    EditFragment editFragment;
    UserFragment userFragment;
    FrameLayout mainFrame;

    public HomeActivity() {
        this.homeViewModel = HomeViewModel.getInstance();
        this.contactFragment = new ContactFragment();
        this.homeFragment = new HomeFragment();
        this.editFragment = new EditFragment();
        this.userFragment = new UserFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mainFrame = findViewById(R.id.main_frame);
        navigationView = findViewById(R.id.space);
        navigationView.initWithSaveInstanceState(savedInstanceState);
        NavBar.setNavBar(navigationView);

        setFragment(homeFragment);
        navigationView.changeCurrentItem(-1);

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                setFragment(homeFragment);
                navigationView.changeCurrentItem(-1);

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        setFragment(contactFragment);
                        break;
                    case 1:
                        setFragment(editFragment);
                        break;
                    case 2:
                        setFragment(userFragment);
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });


//        tv_username = findViewById(R.id.tv_username);
//
//        homeViewModel.getUserEmail().observe(HomeActivity.this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                tv_username.setText(s);
//            }
//        });
//
//        homeViewModel.getCurrentUserEmail();

    }

    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();

        Log.i("FRAGMENT", "changed");
    }
}
